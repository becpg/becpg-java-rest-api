package fr.becpg.api.handler;

import java.util.List;
import java.util.Map;

import org.springframework.lang.NonNull;

import fr.becpg.api.model.RemoteAPIResponse;
import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteEntityList;
import fr.becpg.api.model.RemoteEntityRef;
import fr.becpg.api.model.RemoteEntitySchema;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <p>EntityAPI interface.</p>
 *
 * @author matthieu
 * @version $Id: $Id
 */
public interface EntityAPI {

	/**
	 * <p>list.</p>
	 *
	 * @param query a {@link java.lang.String} object
	 * @return a {@link java.util.List} object
	 */
	List<RemoteEntityRef> list(@NonNull String query);

	/**
	 * <p>list.</p>
	 *
	 * @param query a {@link java.lang.String} object
	 * @param attributes a {@link java.util.List} object
	 * @param maxResults a int
	 * @return a {@link java.util.List} object
	 */
	List<RemoteEntityRef> list(@NonNull String query, List<String> attributes, int maxResults);

	List<RemoteEntityRef> list(RemoteEntity entityQuery, String query, List<String> attributes, int maxResults);
	
	List<RemoteEntityRef> list(RemoteEntity entityQuery);

	/**
	 * <p>list.</p>
	 *
	 * @param query a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param attributes a {@link java.util.List} object
	 * @param maxResults a int
	 * @return a {@link java.util.List} object
	 */
	List<RemoteEntityRef> listByPath(@NonNull String query, @NonNull String path, List<String> attributes, int maxResults);

	Mono<RemoteEntityList> fetchEntityList(@NonNull String query, String path, List<String> attributes, Integer maxResults);
	
	Mono<RemoteEntityList> fetchEntityListPage(@NonNull String query, String path, List<String> attributes, Integer maxResults, Integer page);
	
	Mono<RemoteEntityList> fetchEntityListPage(RemoteEntity entityQuery, String query, String path, List<String> attributes, Integer maxResults,
			Integer page);

	Flux<RemoteEntityList> fetchEntityListAllPages(@NonNull String query, String path, List<String> attributes, Integer maxResults);
	
	Flux<RemoteEntityList> fetchEntityListAllPages(RemoteEntity entityQuery, String query, String path, List<String> attributes, Integer maxResults);
	
	Flux<RemoteEntityList> fetchEntityListAllPages(RemoteEntity entityQuery);

	/**
	 * <p>get.</p>
	 *
	 * @param id a {@link java.lang.String} object
	 * @return a {@link fr.becpg.api.model.RemoteEntity} object
	 */
	RemoteEntity get(String id);

	/**
	 * Retrieves the specified entity.
	 *
	 * The "attributes" and "datalists" parameters can be used to filter the response and reduce its size. There are three types of filtering available:
	 *
	 * - Property filtering: only includes the listed properties (e.g. "fields=bcpg:legalName")
	 * - Association filtering: only includes the listed associations (e.g. "fields=bcpg:clients")
	 * - List filtering: only includes the listed lists (e.g. "lists=bcpg:compoList")
	 *
	 * You can also extract properties of an association by specifying the association name and its properties in the "fields"
	 * parameter, using the following format: "ASSOC_Name1|PROP_Name1,ASSOC_Name1|PROP_Name2".
	 *
	 * The negative form is also supported (available in version 3.1 and above):
	 *
	 * - "lists=!bcpg:activityList"
	 * - "fields=!cm:created,!bcpg:nutListRoundedValue,!cm:modifier,!cm:creator,!cm:modifier,!cm:modified,!bcpg:entityScore,!bcpg:formulatedDate,!bcpg:illLogValue"
	 *
	 * The "params" parameter allows you to pass certain parameters to the API (available in version 3.1 and above):
	 *
	 * - "appendCode" (true): disables the addition of the beCPG code
	 * - "appendErpCode" (true): disables the addition of the ERP code
	 * - "appendMlTextConstraint" (true): disables the addition of multilingual constraints
	 * - "appendNodeRef" (true): disables the addition of the node ID
	 * - "appendDataListNodeRef" (true): disables the addition of the node ID for data list nodes
	 * - "appendContent" (false): enables the inclusion of file content in the JSON response
	 *
	 * @param id a string representing the ID of the entity
	 * @param attributes a list of strings representing the attributes to include in the response
	 * @param datalists a list of strings representing the data lists to include in the
	 * @param params a map of strings to booleans representing the parameters to pass to the API
	 * @return a {@link fr.becpg.api.model.RemoteEntity} object
	 */
	RemoteEntity get(String id, List<String> attributes, List<String> datalists, Map<String, Boolean> params);

	Mono<RemoteEntityRef> fetchEntity(String id, List<String> attributes, List<String> datalists, Map<String, Boolean> params);

	/**
	 * <p>get the entity schema</p>
	 *
	 * @param id a {@link java.lang.String} object
	 * @return a {@link fr.becpg.api.model.RemoteEntitySchema} object
	 */
	RemoteEntitySchema getSchema(String id);

	/**
	 * <p>get the entity schema for a type</p>
	 *
	 * @param type a {@link java.lang.String} object
	 * @return a {@link fr.becpg.api.model.RemoteEntitySchema} object
	 */
	RemoteEntitySchema getSchemaForType(String type);

	/**
	 * Retrieves the specified entity schema.
	 *
	 * The "attributes" and "datalists" parameters can be used to filter the response and reduce its size. There are three types of filtering available:
	 *
	 * - Property filtering: only includes the listed properties (e.g. "fields=bcpg:legalName")
	 * - Association filtering: only includes the listed associations (e.g. "fields=bcpg:clients")
	 * - List filtering: only includes the listed lists (e.g. "lists=bcpg:compoList")
	 *
	 * You can also extract properties of an association by specifying the association name and its properties in the "fields"
	 * parameter, using the following format: "ASSOC_Name1|PROP_Name1,ASSOC_Name1|PROP_Name2".
	 *
	 * The negative form is also supported (available in version 3.1 and above):
	 *
	 * - "lists=!bcpg:activityList"
	 * - "fields=!cm:created,!bcpg:nutListRoundedValue,!cm:modifier,!cm:creator,!cm:modifier,!cm:modified,!bcpg:entityScore,!bcpg:formulatedDate,!bcpg:illLogValue"
	 *
	 * The "params" parameter allows you to pass certain parameters to the API (available in version 3.1 and above):
	 *
	 * - "appendContent" (false): enables the inclusion of file content in the JSON response
	 *
	 * @since 3.4
	 * @param id a string representing the ID of the entity
	 * @param attributes a list of strings representing the attributes to include in the response
	 * @param datalists a list of strings representing the data lists to include in the
	 * @param params a map of strings to booleans representing the parameters to pass to the API
	 * @return a {@link fr.becpg.api.model.RemoteEntitySchema} object
	 */
	RemoteEntitySchema getSchema(String id, List<String> attributes, List<String> datalists, Map<String, Boolean> params);

	/**
	 * Retrieves the  entity schema for specified type
	 *
	 * The "attributes" and "datalists" parameters can be used to filter the response and reduce its size. There are three types of filtering available:
	 *
	 * - Property filtering: only includes the listed properties (e.g. "fields=bcpg:legalName")
	 * - Association filtering: only includes the listed associations (e.g. "fields=bcpg:clients")
	 * - List filtering: only includes the listed lists (e.g. "lists=bcpg:compoList") (Not supported Yet)
	 *
	 * You can also extract properties of an association by specifying the association name and its properties in the "fields"
	 * parameter, using the following format: "ASSOC_Name1|PROP_Name1,ASSOC_Name1|PROP_Name2".
	 *
	 * @since 3.4
	 * @param type a string representing the qname type of th entity
	 * @param attributes a list of strings representing the attributes to include in the response
	 * @param datalists a list of strings representing the data lists to include in the (Not supported Yet)
	 * @param params a map of strings to booleans representing the parameters to pass to the API  (Not supported Yet)
	 * @return a {@link fr.becpg.api.model.RemoteEntitySchema} object
	 */
	RemoteEntitySchema getSchemaForType(String type, List<String> attributes, List<String> datalists, Map<String, Boolean> params);

	/**
	 * <p>check.</p>
	 * return OK/KO if entity exists
	 *
	 * @param id a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	String check(String id);

	/**
	 * <p>update.</p>
	 *
	 * @param entity a {@link fr.becpg.api.model.RemoteEntity} object
	 * @return a {@link fr.becpg.api.model.RemoteEntity} object
	 */
	RemoteEntity update(RemoteEntity entity);

	/**
	 * <p>update.</p>
	 *
	 * @param entity a {@link fr.becpg.api.model.RemoteEntity} object
	 * @param createversion a boolean
	 * @param majorVersion a boolean
	 * @param versionDescription a {@link java.lang.String} object
	 * @return a {@link fr.becpg.api.model.RemoteEntity} object
	 */
	RemoteEntity update(RemoteEntity entity, boolean createversion, boolean majorVersion, String versionDescription);

	Mono<RemoteAPIResponse> updateEntity(RemoteEntity entity, boolean createversion, boolean majorVersion, String versionDescription);

}
