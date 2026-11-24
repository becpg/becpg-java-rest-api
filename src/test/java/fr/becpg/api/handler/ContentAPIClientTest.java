package fr.becpg.api.handler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.DefaultPartHttpMessageReader;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import fr.becpg.api.AbstractRemoteApiTest;
import fr.becpg.api.model.RemoteAPIException;
import fr.becpg.api.model.RemoteDocumentUpload;
import fr.becpg.api.model.RemoteUploadResult;
import fr.becpg.api.model.RemoteVersionUpload;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import reactor.core.publisher.Flux;

/**
 * Upload of a document or of a new version through the Share upload webscript, against a mock repository.
 */
class ContentAPIClientTest extends AbstractRemoteApiTest {

	private static final String UPLOAD_PATH = "/alfresco/service/api/upload";

	private static final String FOLDER_ID = "5c6d7e8f-9a0b-4c1d-2e3f-4a5b6c7d8e9f";

	private static final String FOLDER_NODEREF = "workspace://SpacesStore/" + FOLDER_ID;

	private static final String DOCUMENT_ID = "7f8e9d0c-1b2a-4394-8576-a6b7c8d9e0f1";

	private static final String DOCUMENT_NODEREF = "workspace://SpacesStore/" + DOCUMENT_ID;

	private static final String FILE_NAME = "Technical sheet.pdf";

	/** Not valid UTF-8, so that a text conversion of the content cannot go unnoticed */
	private static final byte[] CONTENT = { '%', 'P', 'D', 'F', 0x00, (byte) 0xFF, (byte) 0xC3, 0x28, (byte) 0x89, '\r', '\n', 0x7F };

	private static final String FILEDATA = "filedata";

	private static final String FILENAME = "filename";

	private static final String DESTINATION = "destination";

	private static final String CONTENTTYPE = "contenttype";

	private static final String UPDATENODEREF = "updatenoderef";

	private static final String MAJORVERSION = "majorversion";

	private static final String DESCRIPTION = "description";

	private static MockWebServer mockBackEnd;

	@Autowired
	private ContentAPI contentApi;

	@BeforeAll
	static void setUp() throws IOException {
		mockBackEnd = new MockWebServer();
		mockBackEnd.start();
	}

	@AfterAll
	static void tearDown() throws IOException {
		mockBackEnd.shutdown();
	}

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry r) {
		r.add("content.service.url", () -> "http://localhost:" + mockBackEnd.getPort());
		// Request building and answer parsing only: no alf_ticket login round-trip (see BasicAuthConfigurationTest)
		r.add("content.service.security.basicAuth.username", () -> "");
	}

	/**
	 * The filedata part must carry a file name: the webscript ignores a filedata part that is not a file
	 */
	@ParameterizedTest
	@NullSource
	@ValueSource(strings = "cm:content")
	void testUploadDocumentSendsTheShareUploadRequestAndReturnsTheDocument(String nodeType) throws Exception {
		mockBackEnd.enqueue(uploadAnswer(DOCUMENT_NODEREF, FILE_NAME));

		RemoteUploadResult result = contentApi.uploadDocument(new RemoteDocumentUpload(FOLDER_ID, FILE_NAME, nodeType),
				new ByteArrayInputStream(CONTENT));

		Assertions.assertEquals(new RemoteUploadResult(DOCUMENT_NODEREF, FILE_NAME, 200), result);
		Map<String, Part> parts = readUploadRequest(mockBackEnd.takeRequest());
		assertFileData(parts);
		Assertions.assertEquals(FILE_NAME, readText(parts.get(FILENAME)));
		Assertions.assertEquals(FOLDER_NODEREF, readText(parts.get(DESTINATION)));
		Assertions.assertEquals(nodeType, parts.containsKey(CONTENTTYPE) ? readText(parts.get(CONTENTTYPE)) : null);
		Assertions.assertFalse(parts.containsKey(UPDATENODEREF));
	}

	@Test
	void testUploadDocumentVersionSendsTheShareUploadRequestAndReturnsTheDocument() throws Exception {
		mockBackEnd.enqueue(uploadAnswer(DOCUMENT_NODEREF, FILE_NAME));

		RemoteUploadResult result = contentApi.uploadDocumentVersion(new RemoteVersionUpload(DOCUMENT_ID, FILE_NAME, true, "Supplier update"),
				new ByteArrayInputStream(CONTENT));

		Assertions.assertEquals(new RemoteUploadResult(DOCUMENT_NODEREF, FILE_NAME, 200), result);
		Map<String, Part> parts = readUploadRequest(mockBackEnd.takeRequest());
		assertFileData(parts);
		Assertions.assertEquals(FILE_NAME, readText(parts.get(FILENAME)));
		Assertions.assertEquals(DOCUMENT_NODEREF, readText(parts.get(UPDATENODEREF)));
		Assertions.assertEquals("true", readText(parts.get(MAJORVERSION)));
		Assertions.assertEquals("Supplier update", readText(parts.get(DESCRIPTION)));
		Assertions.assertFalse(parts.containsKey(DESTINATION));
	}

	@Test
	void testUploadRefusedByTheWebscriptBecomesARemoteError() {
		mockBackEnd.enqueue(new MockResponse().setResponseCode(404).setBody("""
				{"status":{"code":404,"name":"Not Found","description":"The requested resource is not available."},
				 "message":"Destination (%s) not found.","exception":"","callstack":[],"server":"Community","time":"Nov 24, 2026"}
				""".formatted(FOLDER_NODEREF)).addHeader("Content-Type", "application/json;charset=UTF-8"));

		RemoteAPIException exception = Assertions.assertThrows(RemoteAPIException.class,
				() -> contentApi.uploadDocument(new RemoteDocumentUpload(FOLDER_ID, FILE_NAME, null), new ByteArrayInputStream(CONTENT)));

		Assertions.assertEquals("404", exception.getError().getStatus().getCode());
	}

	/**
	 * The mock server is shared by the tests of the class, some of which do not read their requests
	 */
	@BeforeEach
	void drainRequests() throws InterruptedException {
		while (mockBackEnd.takeRequest(0, TimeUnit.MILLISECONDS) != null) {
			// Request of a previous test
		}
	}

	/** The answer of upload.post.json.ftl */
	private MockResponse uploadAnswer(String nodeRef, String fileName) {
		String body = """
				{"nodeRef":"%s","fileName":"%s","status":{"code":200,"name":"OK","description":"File uploaded successfully"}}
				""".formatted(nodeRef, fileName);
		return new MockResponse().setBody(body).addHeader("Content-Type", "application/json;charset=UTF-8");
	}

	private Map<String, Part> readUploadRequest(RecordedRequest request) {
		Assertions.assertEquals("POST", request.getMethod());
		Assertions.assertEquals(UPLOAD_PATH, request.getPath());
		String contentType = request.getHeader("Content-Type");
		Assertions.assertTrue(contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE), contentType);
		byte[] body = request.getBody().readByteArray();
		DataBuffer bodyBuffer = DefaultDataBufferFactory.sharedInstance.wrap(body);
		MockServerHttpRequest multipartRequest = MockServerHttpRequest.post(UPLOAD_PATH).contentType(MediaType.parseMediaType(contentType))
				.body(Flux.just(bodyBuffer));
		ResolvableType partType = ResolvableType.forClass(Part.class);
		return new DefaultPartHttpMessageReader().read(partType, multipartRequest, Map.of()).collectMap(Part::name).block();
	}

	private void assertFileData(Map<String, Part> parts) {
		FilePart fileData = Assertions.assertInstanceOf(FilePart.class, parts.get(FILEDATA));
		Assertions.assertEquals(FILE_NAME, fileData.filename());
		Assertions.assertArrayEquals(CONTENT, readBytes(fileData));
	}

	private String readText(Part part) {
		Assertions.assertNotNull(part);
		byte[] bytes = readBytes(part);
		return new String(bytes, StandardCharsets.UTF_8);
	}

	private byte[] readBytes(Part part) {
		DataBuffer joined = DataBufferUtils.join(part.content()).block();
		byte[] bytes = new byte[joined.readableByteCount()];
		joined.read(bytes);
		DataBufferUtils.release(joined);
		return bytes;
	}

}
