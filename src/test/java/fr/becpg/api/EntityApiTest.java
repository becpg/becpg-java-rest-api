package fr.becpg.api;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.FileCopyUtils;

import fr.becpg.api.handler.ChannelAPI;
import fr.becpg.api.handler.EntityAPI;
import fr.becpg.api.model.ChannelAPIModel;
import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteEntityRef;
import fr.becpg.api.model.RemoteNodeInfo;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@SpringBootTest
class EntityApiTest {

	Logger logger = LoggerFactory.getLogger(EntityApiTest.class);

	public static MockWebServer mockBackEnd;


	@Autowired
	private EntityAPI entityApi;
	

	@Value("classpath:entities.json")
	private Resource entities;

	@Value("classpath:entity.json")
	private Resource entity;
	
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
	    static void properties(DynamicPropertyRegistry r) throws IOException {
	        r.add("content.service.url", () -> "http://localhost:" + mockBackEnd.getPort());
	    }


	

	@Test
	void testEntityApi() {

		mockBackEnd.enqueue(new MockResponse().setBody(asString(entities)).addHeader("Content-Type", "application/json"));


		List<RemoteEntityRef> entities = entityApi.list("+TYPE:\"bcpg:finishedProduct\" AND +bcpg\\:erpCode:\"PERF-PF1\"");
		for (RemoteEntityRef entityRef : entities) {
			

			Assert.assertNotNull(entityRef.getEntity());

			logger.info("Entity ref: " + entityRef.getEntity().toString());
			
			mockBackEnd.enqueue(new MockResponse().setBody(asString(entity)).addHeader("Content-Type", "application/json"));

			RemoteEntity entity = entityApi.get(entityRef.getEntity().getId());

			Assert.assertNotNull(entity);
			Assert.assertNotNull(entity.getName());
			Assert.assertEquals("Tarte coco tradition", entity.getName());
			Assert.assertNotNull(entity.getAttributes());
			Assert.assertNotNull(entity.getDatalists());

			logger.info(entity.getAttributes().get("bcpg:entityTplRef").toString());

			List<RemoteNodeInfo> geoOrigins = entity.getAssociations("bcpg:productGeoOrigin");
			Assert.assertNotNull(geoOrigins);
			Assert.assertEquals(1, geoOrigins.size());

			RemoteNodeInfo entityTpl = entity.getAssociation("bcpg:entityTplRef");
			Assert.assertNotNull(entityTpl);
			Assert.assertNotNull(entityTpl.getName());

			Assert.assertEquals("Produit fini", entityTpl.getName());
			logger.info(entityTpl.getName());

			break;
		}

	}
	

	public static String asString(Resource resource) {
		try (Reader reader = new InputStreamReader(resource.getInputStream(), "UTF-8")) {
			return FileCopyUtils.copyToString(reader);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
