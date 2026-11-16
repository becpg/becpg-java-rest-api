package fr.becpg.api;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import fr.becpg.api.handler.ReportAPI;
import fr.becpg.api.model.RemoteAPIException;
import fr.becpg.api.model.RemoteReport;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import reactor.core.publisher.Mono;

/**
 * The reports of the finished product PF17, as a local 26.2.0 repository exports them (entityReports.json).
 */
class ReportApiTest extends AbstractRemoteApiTest {

	private static final String ENTITY_ID = "86a4c132-5fb4-4542-a4c1-325fb45542da";

	private static final String PRODUCTION_SHEET_ID = "36d302db-c3d1-466f-9302-dbc3d1c66fc2";

	private static final String COST_SHEET_ID = "e254d0b8-aa8b-45fc-94d0-b8aa8b75fcf2";

	private static final String COST_SHEET_TEMPLATE_ID = "638c1af0-6bad-4fad-8c1a-f06bad3fadc7";

	private static final String PDF_CONTENT = "%PDF-1.5 a generated technical sheet, longer than the placeholder";

	@Autowired
	private ReportAPI reportApi;

	@Value("classpath:entityReports.json")
	protected Resource entityReports;

	private static MockWebServer mockBackEnd;

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
		// These tests cover the ReportAPI requests and responses, not authentication.
		r.add("content.service.security.basicAuth.username", () -> "");
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

	@Test
	void testGenerateReportsChecksByDefault() throws InterruptedException {
		mockBackEnd.enqueue(json("{\"generateReport\":true,\"status\":\"SUCCESS\"}"));

		Assert.assertTrue(reportApi.generateReports(ENTITY_ID, false));

		RecordedRequest request = mockBackEnd.takeRequest();
		Assert.assertEquals("GET", request.getMethod());
		// Outside the remote API: reached from the content service URL
		Assert.assertEquals("http://localhost:" + mockBackEnd.getPort() + "/alfresco/service/becpg/entity/generate-report/node/workspace/SpacesStore/"
				+ ENTITY_ID + "/check", request.getRequestUrl().toString());
	}

	@Test
	void testGenerateReportsForced() throws InterruptedException {
		mockBackEnd.enqueue(json("{\"generateReport\":false,\"status\":\"SUCCESS\"}"));

		Assert.assertFalse(reportApi.generateReports("workspace://SpacesStore/" + ENTITY_ID, true));

		Assert.assertTrue(mockBackEnd.takeRequest().getRequestUrl().toString().endsWith("/SpacesStore/" + ENTITY_ID + "/force"));
	}

	@Test
	void testListReports() throws IOException, InterruptedException {
		mockBackEnd.enqueue(json(asString(entityReports)));

		List<RemoteReport> reports = reportApi.listReports(ENTITY_ID);

		Assert.assertEquals(5, reports.size());
		RemoteReport productionSheet = reports.stream().filter(report -> PRODUCTION_SHEET_ID.equals(report.getId())).findFirst().orElseThrow();
		Assert.assertEquals("PF 35808 - Fiche Technique Production.pdf", productionSheet.getName());
		Assert.assertEquals("Fiche Technique Production.rptdesign", productionSheet.getTemplateName());
		Assert.assertEquals(List.of("fr"), productionSheet.getLocales());
		Assert.assertEquals(Boolean.TRUE, productionSheet.isDirty());
		Assert.assertEquals(Boolean.FALSE, productionSheet.isDefault());
		Assert.assertEquals("workspace://SpacesStore/" + PRODUCTION_SHEET_ID, productionSheet.getNodeRef());
		Assert.assertNull("Only generateReport measures the file", productionSheet.getSize());

		RecordedRequest request = mockBackEnd.takeRequest();
		Assert.assertTrue(request.getRequestUrl().encodedPath().endsWith("/becpg/remote/entity"));
		Assert.assertEquals("rep:reports|cm:name,rep:reports|rep:reportLocales,rep:reports|rep:reportTplAssoc,rep:reports|rep:reportIsDirty,"
				+ "rep:reports|rep:reportIsDefault", request.getRequestUrl().queryParameter("fields"));
		// Never an empty lists parameter: it would export every datalist of the product
		Assert.assertEquals("none", request.getRequestUrl().queryParameter("lists"));
	}

	@Test
	void testGenerateReportByTemplateName() throws IOException, InterruptedException {
		mockBackEnd.enqueue(json(asString(entityReports)));
		mockBackEnd.enqueue(new MockResponse().setBody(PDF_CONTENT).addHeader("Content-Type", "application/pdf"));

		RemoteReport report = reportApi.generateReport(ENTITY_ID, "fiche technique production", "FR");

		Assert.assertEquals(PRODUCTION_SHEET_ID, report.getId());
		Assert.assertEquals(Long.valueOf(PDF_CONTENT.length()), report.getSize());
		Assert.assertEquals(Boolean.FALSE, report.isDirty());

		mockBackEnd.takeRequest();
		RecordedRequest contentRequest = mockBackEnd.takeRequest();
		Assert.assertTrue(contentRequest.getRequestUrl().encodedPath().endsWith("/becpg/remote/entity/content"));
		Assert.assertEquals("workspace://SpacesStore/" + PRODUCTION_SHEET_ID, contentRequest.getRequestUrl().queryParameter("nodeRef"));
	}

	@Test
	void testGenerateReportByTemplateIdWithoutLocale() throws IOException {
		mockBackEnd.enqueue(json(asString(entityReports)));
		mockBackEnd.enqueue(new MockResponse().setBody(PDF_CONTENT).addHeader("Content-Type", "application/pdf"));

		RemoteReport report = reportApi.generateReport(ENTITY_ID, "workspace://SpacesStore/" + COST_SHEET_TEMPLATE_ID, null);

		Assert.assertEquals(COST_SHEET_ID, report.getId());
		Assert.assertEquals("Fiche Coûts.rptdesign", report.getTemplateName());
	}

	@Test
	void testGenerateReportCreatesTheReportsOfAnEntityThatHasNone() throws IOException, InterruptedException {
		mockBackEnd.enqueue(json("{\"entity\":{\"id\":\"" + ENTITY_ID + "\",\"type\":\"bcpg:finishedProduct\",\"datalists\":{}}}"));
		mockBackEnd.enqueue(json("{\"generateReport\":true,\"status\":\"SUCCESS\"}"));
		mockBackEnd.enqueue(json(asString(entityReports)));
		mockBackEnd.enqueue(new MockResponse().setBody(PDF_CONTENT).addHeader("Content-Type", "application/pdf"));

		RemoteReport report = reportApi.generateReport(ENTITY_ID, "Fiche Coûts.rptdesign", "fr");

		Assert.assertEquals(COST_SHEET_ID, report.getId());
		Assert.assertTrue(mockBackEnd.takeRequest().getRequestUrl().encodedPath().endsWith("/becpg/remote/entity"));
		Assert.assertTrue(mockBackEnd.takeRequest().getRequestUrl().encodedPath().endsWith("/" + ENTITY_ID + "/check"));
		Assert.assertTrue(mockBackEnd.takeRequest().getRequestUrl().encodedPath().endsWith("/becpg/remote/entity"));
		Assert.assertTrue(mockBackEnd.takeRequest().getRequestUrl().encodedPath().endsWith("/becpg/remote/entity/content"));
	}

	@Test
	void testGenerateReportListsTheAvailableReportsOfAnUnknownTemplate() throws IOException {
		mockBackEnd.enqueue(json(asString(entityReports)));

		IllegalArgumentException exception = Assert.assertThrows(IllegalArgumentException.class,
				() -> reportApi.generateReport(ENTITY_ID, "Fiche Allergènes", "fr"));

		Assert.assertTrue(exception.getMessage(), exception.getMessage().contains("Fiche Coûts.rptdesign [fr]"));
		Assert.assertTrue(exception.getMessage(), exception.getMessage().contains("Fiche Technique Production.rptdesign [fr]"));
	}

	@Test
	void testGenerateReportInAMissingLanguage() throws IOException {
		mockBackEnd.enqueue(json(asString(entityReports)));

		IllegalArgumentException exception = Assert.assertThrows(IllegalArgumentException.class,
				() -> reportApi.generateReport(ENTITY_ID, "Fiche Coûts", "en"));

		Assert.assertTrue(exception.getMessage(), exception.getMessage().contains("in language 'en'"));
	}

	@Test
	void testGenerateReportRefusesThePlaceholder() throws IOException {
		mockBackEnd.enqueue(json(asString(entityReports)));
		mockBackEnd.enqueue(new MockResponse().setBody("Loading ...").addHeader("Content-Type", "text/plain"));

		Assert.assertThrows(IllegalStateException.class, () -> reportApi.generateReport(ENTITY_ID, "Fiche Technique Production", "fr"));
	}

	@Test
	void testGenerateReportRefusesAnEmptyFile() throws IOException {
		mockBackEnd.enqueue(json(asString(entityReports)));
		mockBackEnd.enqueue(new MockResponse().addHeader("Content-Type", "application/pdf"));

		Assert.assertThrows(IllegalStateException.class, () -> reportApi.generateReport(ENTITY_ID, "Fiche Technique Production", "fr"));
	}

	@Test
	void testGenerateEntityReportsReportsAMalformedIdThroughTheMono() {
		Assert.assertEquals(Boolean.FALSE, reportApi.generateEntityReports("workspace://SpacesStore", false)
				.onErrorResume(IllegalArgumentException.class, e -> Mono.just(Boolean.FALSE)).block());
	}

	@Test
	void testGenerateReportReportsTheRepositoryError() {
		mockBackEnd.enqueue(new MockResponse().setResponseCode(403)
				.setBody("{\"status\":{\"code\":403,\"name\":\"Forbidden\",\"description\":\"Forbidden\"},\"message\":\"Access denied\"}")
				.addHeader("Content-Type", "application/json"));

		RemoteAPIException exception = Assert.assertThrows(RemoteAPIException.class,
				() -> reportApi.generateReport(ENTITY_ID, "Fiche Coûts", "fr"));

		Assert.assertEquals("403", exception.getError().getStatus().getCode());
	}

	@Test
	void testGenerateEntityReportReportsAMissingTemplateThroughTheMono() {
		// Building the Mono must not throw: the error reaches the operators of a reactive caller
		Assert.assertEquals("recovered", reportApi.generateEntityReport(ENTITY_ID, null, "fr").map(RemoteReport::getId)
				.onErrorResume(NullPointerException.class, e -> Mono.just("recovered")).block());
	}

	private MockResponse json(String body) {
		return new MockResponse().setBody(body).addHeader("Content-Type", "application/json");
	}

}
