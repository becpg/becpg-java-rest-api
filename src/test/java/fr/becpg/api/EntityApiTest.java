package fr.becpg.api;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.becpg.api.handler.EntityAPI;
import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteEntityRef;
import fr.becpg.api.model.RemoteNodeInfo;

@SpringBootTest
class EntityApiTest {

	@Autowired
	private EntityAPI entityApi;

	 Logger logger = LoggerFactory.getLogger(EntityApiTest.class);
   
	@Test
    void testEntityApi() {
		
		List<RemoteEntityRef> entities =  entityApi.list("+TYPE:\"bcpg:finishedProduct\" AND +bcpg\\:erpCode:\"PERF-PF1\"");
		for(RemoteEntityRef entityRef : entities) {
			Assert.assertNotNull(entityRef.getEntity());
			
		    logger.info("Entity ref: "+entityRef.getEntity().toString() );
			
			RemoteEntity entity = entityApi.get(entityRef.getEntity().getId());
			
			Assert.assertNotNull(entity);
			Assert.assertNotNull(entity.getName());
			Assert.assertEquals("Tarte coco tradition", entity.getName());
			Assert.assertNotNull(entity.getAttributes());
			Assert.assertNotNull(entity.getDatalists());
			
			logger.info(entity.getAttributes().get("bcpg:entityTplRef").toString());
			
			List<RemoteNodeInfo> geoOrigins = entity.getAssociations("bcpg:productGeoOrigin");
			Assert.assertNotNull(geoOrigins);
			Assert.assertEquals(1,geoOrigins.size());
			
			RemoteNodeInfo entityTpl =  entity.getAssociation("bcpg:entityTplRef");
			Assert.assertNotNull(entityTpl);
			Assert.assertNotNull(entityTpl.getName());
			
			Assert.assertEquals("Produit fini", entityTpl.getName());
			logger.info(entityTpl.getName());
			
			break;
		}
		
    }
}
