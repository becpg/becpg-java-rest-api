# Welcome to the beCPG REST API SDK

<p align="center">
  <img title="becpg" alt='becpg' src='docs/images/becpg.png' ></img>
</p>

## What is beCPG REST API?

The beCPG REST API SDK includes a set of APIs and samples that allows developers to quickly build
out-of-process Java applications that integrate with beCPG. <br/>

This SDK provides functionality to consume beCPG REST Remote API.

## Versions

| Version | beCPG Version | Version API | Version JAVA | Changes |
| --- | --- | --- | --- | -- |
| 1.1.1 | >= 4.2.2 | >= 3.3 (Partial support on >=3.1) | JAVA 17 | Migrate To spring boot 3 and Java 17 |
| 1.0.1 | >= 4.2.2 | >= 3.3 (Partial support on >=3.1) | JAVA 11 | Minor fix |
| 1.0.0 | >= 4.2.2 | >= 3.3 (Partial support on >=3.1) | JAVA 11 | Remote API support |

## How does it work?

beCPG REST API SDK  consist of the following  libraries:

* [becpg-java-rest-api](becpg-java-rest-api): Allows applications to consume beCPG public
  REST APIs.

### Pre-Requisites

* Java version 17 or higher
* Maven version 3.3 or higher

### Quick start

#### 1. Create a new Spring Boot application

#### 2. Add these dependencies to your project's build file:

Maven:

First, add to the repositories the beCPG public repository containing the artifacts:

```xml

<repositories>

 	<repository>
		<id>becpg-registry</id>
		<url>artifactregistry://europe-west1-maven.pkg.dev/becpg-314807/becpg-mvn</url>
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
		<version>1.1.0</version>
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


If you are using OAuth2, you can use client-credential based authentication:

```
spring.security.oauth2.client.registration.becpg-java-rest-api.provider=becpg-ids
spring.security.oauth2.client.registration.becpg-java-rest-api.client-id=clientId
spring.security.oauth2.client.registration.becpg-java-rest-api.client-secret=clientSecret
spring.security.oauth2.client.registration.becpg-java-rest-api.authorization-grant-type=client_credentials
spring.security.oauth2.client.provider.becpg-ids.token-uri=${keycloak.auth-server-url}/auth/realms/${keycloak.realm}/protocol/openid-connect/token
```

Or OAuth2 password based authentication:

```
spring.security.oauth2.client.registration.becpg-java-rest-api.provider=becpg-ids
spring.security.oauth2.client.registration.becpg-java-rest-api.client-id=clientId
spring.security.oauth2.client.registration.becpg-java-rest-api.client-secret=clientSecret
spring.security.oauth2.client.registration.becpg-java-rest-api.username=username
spring.security.oauth2.client.registration.becpg-java-rest-api.password=pwd
spring.security.oauth2.client.registration.becpg-java-rest-api.authorization-grant-type=password
spring.security.oauth2.client.provider.becpg-ids.token-uri=${keycloak.auth-server-url}/auth/realms/${keycloak.realm}/protocol/openid-connect/token
```

You can also use or combine with custom header authentication

```
content.service.headers={'x-restricted-custom':'XXXXXX'}
```

Finally, if you want to provide a custom authentication mechanism, you can enable the delegated external authentication:

```
content.service.security.delegated=true
```

And provide a bean that implements the interface **DelegatedAuthenticationProvider**.

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
---

# EntityAPI

This is the documentation for the `EntityAPI` interface.

## Methods

### `List<RemoteEntityRef> list(String query)`

Lists the entities that match the given query.

#### Parameters

- `query`: a string representing the query to execute.

#### Returns

A list of `RemoteEntityRef` objects representing the entities that match the given query.

---

### `List<RemoteEntityRef> list(String query, List<String> attributes, int maxResults)`

Lists the entities that match the given query.

#### Parameters

- `query`: a string representing the query to execute.
- `attributes`: a list of strings representing the attributes to include in the response.
- `maxResults`: an integer representing the maximum number of results to return.

#### Returns

A list of `RemoteEntityRef` objects representing the entities that match the given query.

---

### `RemoteEntity get(String id)`

Retrieves the specified entity.

#### Parameters

- `id`: a string representing the ID of the entity.

#### Returns

A `RemoteEntity` object representing the entity with the specified ID.

---
### `RemoteEntity get(String id, List<String> attributes, List<String> datalists, Map<String, Boolean> params)`

Retrieves the specified entity.

The "attributes" and "datalists" parameters can be used to filter the response and reduce its size. There are three types of filtering available:

- Property filtering: only includes the listed properties (e.g. "fields=bcpg:legalName")
- Association filtering: only includes the listed associations (e.g. "fields=bcpg:clients")
- List filtering: only includes the listed lists (e.g. "lists=bcpg:compoList")

You can also extract properties of an association by specifying the association name and its properties in the "fields" parameter, using the following format: "ASSOC_Name1|PROP_Name1,ASSOC_Name1|PROP_Name2".

The negative form is also supported (available in version 3.1 and above):

- "lists=!bcpg:activityList"
- "fields=!cm:created,!bcpg:nutListRoundedValue,!cm:modifier,!cm:creator,!cm:modifier,!cm:modified,!bcpg:entityScore,!bcpg:formulatedDate,!bcpg:illLogValue"

The "params" parameter allows you to pass certain parameters to the API (available in version 3.1 and above):

- "appendCode" (true): disables the addition of the beCPG code
- "appendErpCode" (true): disables the addition of the ERP code
- "appendMlTextConstraint" (true): disables the addition of multilingual constraints
- "appendNodeRef" (true): disables the addition of the node ID
- "appendDataListNodeRef" (true): disables the addition of the node ID for data list nodes
- "appendContent" (false): enables the inclusion of file content in the JSON response

#### Parameters

- `id`: a string representing the ID of the entity.
- `attributes`: a list of strings representing the attributes to include in the response.
- `datalists`: a list of strings representing the data lists to include in the response.
- `params`: a map of strings to booleans representing the parameters to pass to the API.

#### Returns

A `RemoteEntity` object representing the entity with the specified ID.


---

### `String check(String id)`

Checks if an entity with the given ID exists.

#### Parameters

- `id`: a string representing the ID of the entity.

#### Returns

A string representing the result of the check. The string will be `"OK"` if the entity exists, and `"KO"` if it does not.

---

### `RemoteEntity update(RemoteEntity entity)`

Updates the specified entity.

#### Parameters

- `entity`: a `RemoteEntity` object representing the entity to update.

#### Returns

A `RemoteEntity` object representing the updated entity.




# ChannelAPI

The `ChannelAPI` interface provides methods for accessing and manipulating channels.

## Methods

### `List<RemoteEntityRef> list(String channelId)`

Lists the entities in the specified channel.

#### Parameters
- `channelId`: a string representing the ID of the channel.

#### Returns
- a list of `RemoteEntityRef` objects representing the entities in the channel.

### `List<RemoteEntityRef> list(String channelId, List<String> attributes, int maxResults)`

Lists the entities in the specified channel, including the specified attributes.

#### Parameters
- `channelId`: a string representing the ID of the channel.
- `attributes`: a list of strings representing the attributes to include in the list.
- `maxResults`: an integer representing the maximum number of results to return.

#### Returns
- a list of `RemoteEntityRef` objects representing the entities in the channel.

### `RemoteEntity get(String channelId)`

Gets the specified channel.

#### Parameters
- `channelId`: a string representing the ID of the channel.

#### Returns
- a `RemoteEntity` object representing the channel.


# ContentAPI

This interface provides methods for interacting with content in the system.

---

### `String getOrCreateSharedUrl(RemoteNodeInfo remoteNodeInfo) throws IOException`

Gets or creates a shared URL for the given node.

#### Parameters

- `remoteNodeInfo`: a `RemoteNodeInfo` object representing the node for which to get or create a shared URL.

#### Returns

A string representing the shared URL for the node.

#### Throws

- `IOException`: if there is an error while getting or creating the shared URL.

---

### `void writeContent(RemoteNodeInfo remoteNodeInfo, Path destFile) throws IOException`

Writes the content of the given node to the specified file.

#### Parameters

- `remoteNodeInfo`: a `RemoteNodeInfo` object representing the node whose content to write.
- `destFile`: a `Path` object representing the file to write the content to.

#### Throws

- `IOException`: if there is an error while writing the content.

