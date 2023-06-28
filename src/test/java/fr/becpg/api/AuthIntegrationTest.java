package fr.becpg.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import fr.becpg.api.handler.EntityAPI;
import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteEntityRef;
import fr.becpg.api.model.RemoteNodeInfo;

@SpringBootTest
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
class AuthIntegrationTest {

	Logger logger = LoggerFactory.getLogger(AuthIntegrationTest.class);


	@Autowired
	private EntityAPI entityApi;	

	@Test
	void testEntityApi() throws JSONException {

		List<RemoteEntityRef> entities = entityApi.list("+TYPE:\"bcpg:finishedProduct\" AND +bcpg\\:erpCode:\"PERF-PF1\"");
		for (RemoteEntityRef entityRef : entities) {
			

			Assert.assertNotNull(entityRef.getEntity());

			logger.info("Entity ref: " + entityRef.getEntity().toString());
			
			 Map<String,Boolean> params = new HashMap<>();
			JSONObject jsonObject = new JSONObject("{\"appendCode\":false,\"appendErpCode\":false,\"appendNodeRef\":false,\"appendMlTextConstraint\":false,\"appendDataListNodeRef\":false}");
			@SuppressWarnings("unchecked")
			Iterator<String> keys = jsonObject.keys();
			while ( keys.hasNext()) {
			    String key = keys.next();
			    Boolean value = jsonObject.getBoolean(key);
			    params.put(key, value);
			}
			

			RemoteEntity entity = entityApi.get(entityRef.getEntity().getId(),new ArrayList<>(), new ArrayList<>(), params);

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
	
}
