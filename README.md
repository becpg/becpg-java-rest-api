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
| 1.1.19 | >= 23.4.2 | >= 3.5 | JAVA 17 | Apply the `remote.*` client settings (trust-all SSL, HTTP/1.1, TLSv1.2, timeouts) to the Alfresco login call and to the OAuth2 token call, not only to the API calls; drop the deprecated `password` grant from the documentation |
| 1.1.18 | >= 23.4.2 | >= 3.5 | JAVA 17 | Read a `d:date` published as a calendar day, keeping the instant form of earlier repositories; provide the reactive OAuth2 client registration repository in servlet applications; bump Spring Boot to 3.5.16 (CVE-2026-22732) |
| 1.1.17 | >= 23.4.2 | >= 3.5 | JAVA 17 | Add ingList field definitions |
| 1.1.16 | >= 23.4.2 | >= 3.5 | JAVA 17 | Add configurable HTTP connect/response timeouts; reduce Keycloak load (cache out-of-session alf_ticket, refresh tokens); fix OAuth2 conditional activation |
| 1.1.15 | >= 23.4.2 | >= 3.5 | JAVA 17 | Add ProjectAPIModel, person properties and validation constraints |
| 1.1.14 | >= 23.4.2 | >= 3.5 | JAVA 17 | Add secure batch endpoints in ChannelAPI |
| 1.1.13 | >= 23.4.2 | >= 3.5 | JAVA 17 | Add bcpg:allergenListQtyPerc field constant |
| 1.1.12 | >= 23.4.2 | >= 3.5 | JAVA 17 | Fix alf ticket encoding |
| 1.1.11 | >= 23.4.2 | >= 3.5 | JAVA 17 | Bug fix oauth |
| 1.1.10 | >= 23.4.2 | >= 3.5 | JAVA 17 | Add alfTocken session in basic Auth |
| 1.1.9 | >= 23.4.2 | >= 3.5 | JAVA 17 | Add common helper functions |
| 1.1.8 | >= 23.4.2 | >= 3.5 | JAVA 17 | Add BecpgAPIModel, setAssociations helpers, HTTP/1.1 and TLSv1.2 forcing options |
| 1.1.7 | >= 23.4.2 | >= 3.5 (Partial support on >=3.3) | JAVA 17 | Bug fixes |
| 1.1.6 | >= 23.4.2 | >= 3.5 (Partial support on >=3.3) | JAVA 17 | Add advanced search support |
| 1.1.5 | >= 4.2.3 | >= 3.5 (Partial support on >=3.3) | JAVA 17 | Fix cm:person charact and single assoc |
| 1.1.4 | >= 4.2.3 | >= 3.5 (Partial support on >=3.3) | JAVA 17 | Fix Memory issue |
| 1.1.3 | >= 4.2.3 | >= 3.5 (Partial support on >=3.3) | JAVA 17 | Bugfix in compressParam |
| 1.1.2 | >= 4.2.3 | >= 3.5 (Partial support on >=3.3) | JAVA 17 | Add entity schema support |
| 1.1.1 | >= 4.2.2 | >= 3.4 (Partial support on >=3.3) | JAVA 17 | Add nested datalist getter (API >=3.4) |
| 1.1.0 | >= 4.2.2 | >= 3.3 (Partial support on >=3.1) | JAVA 17 | Migrate To spring boot 3 and Java 17 |
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
		<version>1.1.19</version>
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

The `password` grant is no longer supported: Spring Security deprecated it (it is dropped by OAuth
2.1 and removed in Spring Security 7). Use `client_credentials` for a technical account, or Basic
authentication if you need to act as a named user.

You can also use or combine with custom header authentication

```
content.service.headers={'x-restricted-custom':'XXXXXX'}
```

Finally, if you want to provide a custom authentication mechanism, you can enable the delegated external authentication:

```
content.service.security.delegated=true
```

And provide a bean that implements the interface **DelegatedAuthenticationProvider**.

#### OAuth2 bearer token reuse (recommended)

When OAuth2 is enabled, the SDK reuses the bearer token during a `doInSession(...)` scope and only reacquires a token on `401` responses.

Behavior:

* Avoids token acquisition on every request inside a connector job/session.
* Automatically retries authentication when the token is expired or rejected (HTTP 401).
* Applies to the `client_credentials` and `refresh_token` grants.

#### Basic Auth Alfresco ticket reuse

When Basic authentication is enabled, the SDK can reuse an Alfresco ticket in a `doInSession(...)` scope:

* The first call performs an Alfresco login (`/alfresco/service/api/login`) using configured Basic credentials.
* The returned `alf_ticket` is reused for subsequent requests in the same scope.
* On `401`, the SDK refreshes the ticket and retries the request once.
* Outside `doInSession(...)`, the SDK keeps direct Basic authentication on each request.

#### SSL

It is possible to disable SSL verification with the option (For testing only for security reasons):

```
remote.ssl.trustAll=true
```

If the target server has issues with HTTP/2 or TLS negotiation, you can force HTTP/1.1 or TLSv1.2:

```
remote.force.http1=true
remote.force.tls12=true
```

These options apply to every call made by the SDK, not only the API calls. Three clients are
involved and they are all built from the same settings by `RemoteHttpClientFactory`:

* the API calls, through the remote `WebClient` (reactor-netty),
* the Alfresco login call (`/alfresco/service/api/login`) of Basic authentication, which runs on a
  JDK HTTP client because it is made synchronously from within the authentication filter,
* the OAuth2 token call, which Spring Security makes with a `WebClient` of its own.

With `remote.ssl.trustAll=true` these clients accept any server certificate, whatever its issuer and
whatever the hostname it was issued for — so a repository or an identity provider published behind a
self-signed or company-signed certificate is reachable with either authentication mode.

#### Compress params

If fields param reach the connector max request size limit its possible to compress header:

```
remote.compress.param=true
```

#### Timeouts

The HTTP clients are bounded by two timeouts so that a dropped or half-open connection (e.g. an
unstable WAN/satellite link) makes the call fail fast instead of blocking the caller indefinitely —
all API methods block on the response, and so do the login and token calls:

```
# Max time (ms) to establish the TCP connection. 0 disables the bound. Default: 30000
remote.connect.timeout=30000

# Max time (s) to fully receive a response once the request is sent. A timeout raises a
# ReadTimeoutException instead of hanging forever. 0 disables the bound. Default: 300
remote.response.timeout=300
```

#### Dates

A property carries a date in one of two shapes, and `RemoteNodeInfo.getDateProp(...)` reads both, so
one build of your application works against the whole range of supported repositories:

* a `d:datetime` is a point in time, published as a UTC instant — `2026-02-27T18:23:25.906Z`;
* a `d:date` is a calendar day. beCPG 26.1 and above publish it as a plain day — `2027-07-01`.
  Earlier versions published the instant that day happens to be stored at, which reads as the
  previous day whenever the repository runs ahead of UTC.

A plain day is anchored at midnight in the time zone of the JVM that reads it, so formatting it back
out yields the same day whatever that time zone is. An instant keeps its own meaning: rendering it
is still subject to the time zone you render it in.

`DateExtractorHelper.isDate(...)` tells both shapes apart from anything else, and
`DateExtractorHelper.parse(...)` turns either into a `java.util.Date`. A value `isDate` rejects is
left as text, which is what you want for a property that merely looks like a date.


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

### `List<RemoteEntityRef> list(String query, List<String> attributes, int maxResults, Map<String, Boolean> params)`

Lists the entities that match the given query.

#### Parameters

- `query`: a string representing the query to execute.
- `attributes`: a list of strings representing the attributes to include in the response.
- `maxResults`: an integer representing the maximum number of results to return.
- `params`: a map of strings to booleans representing the parameters to pass to the API (e.g., "appendCode", "appendErpCode", etc.).

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

---

### `RemoteAPIResponse batchStart(String channelId, String batchId)`

Starts a batch for the specified channel.

#### Parameters
- `channelId`: a string representing the ID of the channel.
- `batchId`: a string representing the ID of the batch.

#### Returns
- a `RemoteAPIResponse` object representing the response of the remote API.

---

### `RemoteAPIResponse batchAck(String channelId, String nodeRef, Map<String, Object> attributes)`

Updates the channel status on an entity within a batch.

#### Parameters
- `channelId`: a string representing the ID of the channel.
- `nodeRef`: a string representing the NodeRef of the entity.
- `attributes`: a map of strings to objects representing the attributes to update.

#### Returns
- a `RemoteAPIResponse` object representing the response of the remote API.

---

### `RemoteAPIResponse batchEnd(String channelId, Map<String, Object> attributes)`

Ends the batch for the specified channel.

#### Parameters
- `channelId`: a string representing the ID of the channel.
- `attributes`: a map of strings to objects representing the channel attributes to update at the end of the batch.

#### Returns
- a `RemoteAPIResponse` object representing the response of the remote API.


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
