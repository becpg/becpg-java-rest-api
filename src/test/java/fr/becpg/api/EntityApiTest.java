package fr.becpg.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import fr.becpg.api.handler.EntityAPI;
import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteEntityRef;
import fr.becpg.api.model.RemoteNodeInfo;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

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
	}

	@Test
	void testEntityApi() throws JSONException, IOException {

		mockBackEnd.enqueue(new MockResponse().setBody(asString(entities)).addHeader("Content-Type", "application/json"));

		List<RemoteEntityRef> entities = entityApi.list("+TYPE:\"bcpg:finishedProduct\" AND +bcpg\\:erpCode:\"PERF-PF1\"");
		for (RemoteEntityRef entityRef : entities) {

			Assert.assertNotNull(entityRef.getEntity());

			logger.info("Entity ref: " + entityRef.getEntity().toString());

			mockBackEnd.enqueue(new MockResponse().setBody(asString(entity)).addHeader("Content-Type", "application/json"));

			Map<String, Boolean> params = new HashMap<>();
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
	
}
