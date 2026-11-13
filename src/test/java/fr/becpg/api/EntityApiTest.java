package fr.becpg.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import fr.becpg.api.handler.EntityAPI;
import fr.becpg.api.model.BeCPGAPIModel;
import fr.becpg.api.model.RemoteAPIException;
import fr.becpg.api.model.RemoteAPIResponse;
import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteEntityRef;
import fr.becpg.api.model.RemoteNodeInfo;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import reactor.core.publisher.Mono;

class EntityApiTest extends AbstractRemoteApiTest {

	Logger logger = LoggerFactory.getLogger(EntityApiTest.class);

	@Autowired
	private EntityAPI entityApi;

	private static MockWebServer mockBackEnd;

	@Value("classpath:entitiesWithMoreItems.json")
	protected Resource entitiesWithMoreItems;

	@Value("classpath:entitiesWithoutMoreItems.json")
	protected Resource entitiesWithoutMoreItems;

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
	static void properties(DynamicPropertyRegistry r)  {
		r.add("content.service.url", () -> "http://localhost:" + mockBackEnd.getPort());
		// These tests cover the EntityAPI request building / response parsing, not authentication.
		// Disable Basic auth so the WebClient hits the mock directly (no alf_ticket login round-trip).
		// The Basic-auth ticket flow is covered by BasicAuthConfigurationTest.
		r.add("content.service.security.basicAuth.username", () -> "");
	}

	@Test
	void testEntityApi() throws JSONException, IOException {

		mockBackEnd.enqueue(new MockResponse().setBody(asString(entities)).addHeader("Content-Type", "application/json"));

		List<RemoteEntityRef> entities = entityApi.list("+TYPE:\"bcpg:finishedProduct\" AND +bcpg\\:erpCode:\"PERF-PF1\"");
		for (RemoteEntityRef entityRef : entities) {

			Assert.assertNotNull(entityRef.getEntity());

			logger.info("Entity ref: " + entityRef.getEntity().toString());

			mockBackEnd.enqueue(new MockResponse().setBody(asString(entity)).addHeader("Content-Type", "application/json"));

			Map<String, Object> params = new HashMap<>();
			JSONObject jsonObject = new JSONObject(
					"{\"appendCode\":false,\"appendErpCode\":false,\"appendNodeRef\":false,\"appendMlTextConstraint\":false,\"appendDataListNodeRef\":false}");
			@SuppressWarnings("unchecked")
			Iterator<String> keys = jsonObject.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				Boolean value = jsonObject.getBoolean(key);
				params.put(key, value);
			}

			RemoteEntity entity = entityApi.get(entityRef.getEntity().getId(), new ArrayList<>(), new ArrayList<>(), params);

			Assert.assertNotNull(entity);
			Assert.assertNotNull(entity.getName());
			Assert.assertEquals("Tarte coco tradition", entity.getName());
			Assert.assertNotNull(entity.getAttributes());
			Assert.assertNotNull(entity.getDatalists());
			Assert.assertEquals(14, entity.getDatalistItems(BeCPGAPIModel.TYPE_ORGANOLIST).size());
			
			List<RemoteNodeInfo> geoOrigins = entity.getAssociations("bcpg:productGeoOrigin");
			Assert.assertNotNull(geoOrigins);
			Assert.assertEquals(1, geoOrigins.size());

			RemoteNodeInfo entityTpl = entity.getAssociation("bcpg:entityTplRef");
			Assert.assertNotNull(entityTpl);
			Assert.assertNotNull(entityTpl.getName());


			logger.info(entityTpl.toString());
			
			Assert.assertEquals("Produit fini", entityTpl.getName());
			logger.info(entityTpl.getName());

			break;
		}

	}
	
	@Test
	void testListAllPages() throws IOException {

		mockBackEnd.enqueue(new MockResponse().setBody(asString(entitiesWithMoreItems)).addHeader("Content-Type", "application/json"));
		mockBackEnd.enqueue(new MockResponse().setBody(asString(entitiesWithMoreItems)).addHeader("Content-Type", "application/json"));
		mockBackEnd.enqueue(new MockResponse().setBody(asString(entitiesWithMoreItems)).addHeader("Content-Type", "application/json"));
		mockBackEnd.enqueue(new MockResponse().setBody(asString(entitiesWithMoreItems)).addHeader("Content-Type", "application/json"));
		mockBackEnd.enqueue(new MockResponse().setBody(asString(entitiesWithoutMoreItems)).addHeader("Content-Type", "application/json"));

		List<RemoteEntityRef> entities = entityApi.list("+TYPE:\"bcpg:finishedProduct\"", Arrays.asList("cm:name"), -1);
		assertEquals(5, entities.size());
	}
	
	@Test
    void testURLEncoding() throws  IOException, InterruptedException {
        
        mockBackEnd.enqueue(new MockResponse().setBody(asString(entities)).addHeader("Content-Type", "application/json"));

        entityApi.list("+TYPE:\"bcpg:finishedProduct\" AND +bcpg\\:erpCode:\"PERF-PF1\"", List.of("cm:name","bcpg:legalName"), 10);

        Assert.assertEquals("http://localhost:" + mockBackEnd.getPort() + "/alfresco/service/becpg/remote/entity/list?format=json&query=%2BTYPE%3A%22bcpg%3AfinishedProduct%22%20AND%20%2Bbcpg%5C%3AerpCode%3A%22PERF-PF1%22&maxResults=10&fields=cm%3Aname%2Cbcpg%3AlegalName", mockBackEnd.takeRequest().getRequestUrl().toString());
    
    }

	@Test
	void testListWithParams() throws IOException, InterruptedException {
		mockBackEnd.enqueue(new MockResponse().setBody(asString(entities)).addHeader("Content-Type", "application/json"));

		Map<String, Boolean> params = Map.of("appendContent", true);
		entityApi.list("+TYPE:\"bcpg:finishedProduct\"", List.of("cm:name"), 10, params);

		String requestedUrl = mockBackEnd.takeRequest().getRequestUrl().toString();
		Assert.assertTrue(requestedUrl.contains("jsonParamappendContent=true"));
	}

	@Test
	void testFormulate() throws InterruptedException {
		mockBackEnd.enqueue(okStatus(ENTITY_NODEREF));

		RemoteAPIResponse response = entityApi.formulate(ENTITY_ID);

		Assert.assertEquals("SUCCESS", response.getStatus());
		Assert.assertEquals(ENTITY_ID, response.getId());

		RecordedRequest request = mockBackEnd.takeRequest();
		Assert.assertEquals("GET", request.getMethod());
		Assert.assertEquals(remoteUrl() + "/formulate?format=json&nodeRef=" + ENTITY_NODEREF, request.getRequestUrl().toString());
	}

	@Test
	void testFormulateNotCompleted() throws InterruptedException {
		// The repository answers 200 without a body when the formulation did not complete
		mockBackEnd.enqueue(new MockResponse());

		Assert.assertNull(entityApi.formulate(ENTITY_ID));
	}

	@Test
	void testFormulateReportsTheRepositoryError() {
		mockBackEnd.enqueue(new MockResponse().setResponseCode(403)
				.setBody("{\"status\":{\"code\":403,\"name\":\"Forbidden\",\"description\":\"Forbidden\"},\"message\":\"Access denied\"}")
				.addHeader("Content-Type", "application/json"));

		RemoteAPIException exception = Assert.assertThrows(RemoteAPIException.class, () -> entityApi.formulate(ENTITY_ID));

		Assert.assertEquals("403", exception.getError().getStatus().getCode());
		Assert.assertEquals("Access denied", exception.getError().getMessage());
	}

	@Test
	void testRateLimitIsReported() {
		mockBackEnd.enqueue(new MockResponse().setResponseCode(429).addHeader("Retry-After", "1"));

		RemoteAPIException exception = Assert.assertThrows(RemoteAPIException.class, () -> entityApi.delete(ENTITY_ID));

		Assert.assertEquals("429", exception.getError().getStatus().getCode());
	}

	@Test
	void testBranch() throws InterruptedException {
		mockBackEnd.enqueue(okStatus(BRANCH_NODEREF));

		RemoteAPIResponse response = entityApi.branch(ENTITY_ID, FOLDER_ID);

		Assert.assertEquals(BRANCH_ID, response.getId());

		RecordedRequest request = mockBackEnd.takeRequest();
		Assert.assertEquals("POST", request.getMethod());
		Assert.assertEquals(remoteUrl() + "/branch?format=json&nodeRef=" + ENTITY_NODEREF + "&destNodeRef=workspace://SpacesStore/" + FOLDER_ID,
				request.getRequestUrl().toString());
	}

	@Test
	void testBranchNextToTheEntity() throws InterruptedException {
		mockBackEnd.enqueue(okStatus(BRANCH_NODEREF));

		entityApi.branch(ENTITY_ID, null);

		Assert.assertEquals(remoteUrl() + "/branch?format=json&nodeRef=" + ENTITY_NODEREF, mockBackEnd.takeRequest().getRequestUrl().toString());
	}

	@Test
	void testMerge() throws InterruptedException, JSONException {
		mockBackEnd.enqueue(okStatus(ENTITY_NODEREF));

		RemoteAPIResponse response = entityApi.merge(BRANCH_ID, ENTITY_NODEREF, true, "New recipe & packaging", false);

		Assert.assertEquals(ENTITY_ID, response.getId());

		RecordedRequest request = mockBackEnd.takeRequest();
		Assert.assertEquals("POST", request.getMethod());
		Assert.assertEquals(remoteUrl() + "/merge?format=json&nodeRef=workspace://SpacesStore/" + BRANCH_ID + "&branchToNodeRef="
				+ ENTITY_NODEREF + "&majorVersion=true&impactWused=false", request.getRequestUrl().toString());
		Assert.assertTrue(request.getHeader("Content-Type").startsWith("application/json"));
		Assert.assertEquals("New recipe & packaging", new JSONObject(request.getBody().readUtf8()).getString("description"));
	}

	@Test
	void testMergeWithoutDescription() throws InterruptedException, JSONException {
		mockBackEnd.enqueue(okStatus(ENTITY_NODEREF));

		entityApi.merge(BRANCH_ID, ENTITY_ID, false, null, true);

		RecordedRequest request = mockBackEnd.takeRequest();
		Assert.assertTrue(request.getRequestUrl().toString().endsWith("&majorVersion=false&impactWused=true"));
		Assert.assertEquals("", new JSONObject(request.getBody().readUtf8()).getString("description"));
	}

	@Test
	void testMergeRequiresTheTargetEntity() {
		Assert.assertThrows(NullPointerException.class, () -> entityApi.merge(BRANCH_ID, null, false, null, false));
	}

	@Test
	void testMergeEntityReportsTheMissingTargetThroughTheMono() {
		// Building the Mono must not throw: the error reaches the operators of a reactive caller
		Mono<RemoteAPIResponse> merge = entityApi.mergeEntity(BRANCH_ID, null, false, null, false);

		Assert.assertEquals("recovered", merge.map(RemoteAPIResponse::getStatus).onErrorResume(NullPointerException.class, e -> Mono.just("recovered")).block());
	}

	@Test
	void testDelete() throws InterruptedException {
		mockBackEnd.enqueue(okStatus(ENTITY_NODEREF));

		RemoteAPIResponse response = entityApi.delete(ENTITY_NODEREF);

		Assert.assertEquals(ENTITY_ID, response.getId());

		RecordedRequest request = mockBackEnd.takeRequest();
		Assert.assertEquals("DELETE", request.getMethod());
		Assert.assertEquals(remoteUrl() + "/entity?format=json&nodeRef=" + ENTITY_NODEREF, request.getRequestUrl().toString());
	}

	@Test
	void testDeleteOnRepositoryWithoutTheEntity() {
		mockBackEnd.enqueue(new MockResponse().setResponseCode(404)
				.setBody("<html><head><title>Web Script Status 404 - Not Found</title></head><body>Not Found</body></html>")
				.addHeader("Content-Type", "text/html;charset=UTF-8"));

		RemoteAPIException exception = Assert.assertThrows(RemoteAPIException.class, () -> entityApi.delete(ENTITY_ID));

		Assert.assertEquals("404", exception.getError().getStatus().getCode());
	}

	private static final String ENTITY_ID = "9b4dd09a-afaa-41ec-84eb-db062146975c";

	private static final String ENTITY_NODEREF = "workspace://SpacesStore/" + ENTITY_ID;

	private static final String BRANCH_ID = "0a5b9d3c-1f2e-4c6d-8e7f-9a0b1c2d3e4f";

	private static final String BRANCH_NODEREF = "workspace://SpacesStore/" + BRANCH_ID;

	private static final String FOLDER_ID = "5c6d7e8f-9a0b-4c1d-2e3f-4a5b6c7d8e9f";

	private static String remoteUrl() {
		return "http://localhost:" + mockBackEnd.getPort() + "/alfresco/service/becpg/remote";
	}

	private MockResponse okStatus(String nodeRef) {
		return new MockResponse().setBody("{\"nodeRef\":\"" + nodeRef + "\",\"status\":\"SUCCESS\"}").addHeader("Content-Type", "application/json");
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

}
