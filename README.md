# Welcome to the beCPG REST API SDK

<p align="center">
  <img title="becpg" alt='becpg' src='docs/images/becpg.png' ></img>
</p>

## What is beCPG REST API?

The beCPG REST API SDK includes a set of APIs and samples that allows developers to quickly build
out-of-process Java applications that integrate with beCPG. <br/>

This SDK provides functionality to consume beCPG REST Remote API.

## How does it work?

beCPG REST API SDK  consist of the following  libraries:

* [becpg-java-rest-api](becpg-java-rest-api): Allows applications to consume beCPG public
  REST APIs.

### Pre-Requisites

* Java version 11 or higher
* Maven version 3.3 or higher

### Quick start

#### 1. Create a new Spring Boot application

#### 2. Add these dependencies to your project's build file:

Maven:

First, add to the repositories the beCPG public repository containing the artifacts:

```xml

<repositories>

  <repository>
    <id>becpg-public</id>
    <url>TODO</url>
  </repository>

</repositories>
```

Then, add the dependency on the desired starter(s)

```xml

<dependencies>

  <!-- Java REST API -->
   <dependency>
       <groupId>fr.becpg</groupId>
	    <artifactId>becpg-java-rest-api</artifactId>
		<version>1.0.0</version>
    </dependency>  
        
</dependencies>
```


#### 4. Configure REST API

In your ```application.properties``` file provide URL, authentication mechanism and credentials for
accessing the REST API:

```
content.service.url=http://repository:8080
content.service.security.basicAuth.username=admin
content.service.security.basicAuth.password=admin
```

#### 5. Consume the REST API

```java
   @Autowired
	private EntityApi entityApi;

	@Test
    void testEntityApi() {
		
		List<RemoteEntityRef> entities =  entityApi.list("+TYPE:\"bcpg:finishedProduct\" AND +bcpg\\:erpCode:\"PERF-PF1\"");
		for(RemoteEntityRef entityRef : entities) {
		   RemoteEntity entity = entityApi.get(entityRef.getEntity().getId());
	
			Assert.assertNotNull(entity.getName());
			Assert.assertNotNull(entity.getAttributes());
			Assert.assertNotNull(entity.getDatalists());
			
			logger.info(entity.getAttributes().get("bcpg:entityTplRef").toString());
			
			List<RemoteNodeInfo> geoOrigins = entity.getAssociations("bcpg:productGeoOrigin");

			RemoteNodeInfo entityTpl =  entity.getAssociation("bcpg:entityTplRef");
			
			break;
		}
   
   
```

