package fr.becpg.api;

import java.util.List;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import fr.becpg.api.handler.EntityAPI;
import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteEntityRef;

@SpringBootTest
@ActiveProfiles({"integration"})
@TestPropertySource(
		  locations = "classpath:application-oauth.properties")
class OAuthIntegrationTest {

	Logger logger = LoggerFactory.getLogger(OAuthIntegrationTest.class);


	@Autowired
	private EntityAPI entityApi;	

	@Test
	void testEntityApi() throws JSONException {

		List<RemoteEntityRef> entities = entityApi.list("+TYPE:\"bcpg:finishedProduct\" AND +bcpg\\:erpCode:\"PERF-PF1\"");
		for (RemoteEntityRef entityRef : entities) {
			
			Assert.assertNotNull(entityRef.getEntity());

			logger.info("Entity ref: " + entityRef.getEntity().toString());
		
			RemoteEntity entity = entityApi.get(entityRef.getEntity().getId());

			Assert.assertNotNull(entity);
			Assert.assertNotNull(entity.getName());
			Assert.assertEquals("Tarte coco tradition", entity.getName());
			Assert.assertNotNull(entity.getAttributes());
			Assert.assertNotNull(entity.getDatalists());

			break;
		}

	}
	
}
