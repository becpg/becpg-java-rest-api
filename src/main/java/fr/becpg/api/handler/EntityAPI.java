package fr.becpg.api.handler;

import java.util.List;
import java.util.Map;

import org.jspecify.annotations.NonNull;

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

        /**
         * <p>list.</p>
         *
         * @param query a {@link java.lang.String} object
         * @param attributes a {@link java.util.List} object
         * @param maxResults a int
         * @param params a {@link java.util.Map} object
         * @return a {@link java.util.List} object
         */
        List<RemoteEntityRef> list(@NonNull String query, List<String> attributes, int maxResults, Map<String, Boolean> params);

	/**
	 * <p>list.</p>
	 *
	 * @param entityQuery a {@link fr.becpg.api.model.RemoteEntity} object
	 * @param query a {@link java.lang.String} object
	 * @param attributes a {@link java.util.List} object
	 * @param maxResults a int
	 * @return a {@link java.util.List} object
	 */
	List<RemoteEntityRef> list(RemoteEntity entityQuery, String query, List<String> attributes, int maxResults);
	
	/**
	 * <p>list.</p>
	 *
	 * @param entityQuery a {@link fr.becpg.api.model.RemoteEntity} object
	 * @return a {@link java.util.List} object
	 */
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

	/**
	 * <p>fetchEntityList.</p>
	 *
	 * @param query a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param attributes a {@link java.util.List} object
	 * @param maxResults a {@link java.lang.Integer} object
	 * @return a {@link reactor.core.publisher.Mono} object
	 */
	Mono<RemoteEntityList> fetchEntityList(@NonNull String query, String path, List<String> attributes, Integer maxResults);

        /**
         * <p>fetchEntityList.</p>
         *
         * @param query a {@link java.lang.String} object
         * @param path a {@link java.lang.String} object
         * @param attributes a {@link java.util.List} object
         * @param maxResults a {@link java.lang.Integer} object
         * @param params a {@link java.util.Map} object
         * @return a {@link reactor.core.publisher.Mono} object
         */
        Mono<RemoteEntityList> fetchEntityList(@NonNull String query, String path, List<String> attributes, Integer maxResults, Map<String, Boolean> params);
	
	/**
	 * <p>fetchEntityListPage.</p>
	 *
	 * @param query a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param attributes a {@link java.util.List} object
	 * @param maxResults a {@link java.lang.Integer} object
	 * @param page a {@link java.lang.Integer} object
	 * @return a {@link reactor.core.publisher.Mono} object
	 */
	Mono<RemoteEntityList> fetchEntityListPage(@NonNull String query, String path, List<String> attributes, Integer maxResults, Integer page);

        /**
         * <p>fetchEntityListPage.</p>
         *
         * @param query a {@link java.lang.String} object
         * @param path a {@link java.lang.String} object
         * @param attributes a {@link java.util.List} object
         * @param maxResults a {@link java.lang.Integer} object
         * @param page a {@link java.lang.Integer} object
         * @param params a {@link java.util.Map} object
         * @return a {@link reactor.core.publisher.Mono} object
         */
        Mono<RemoteEntityList> fetchEntityListPage(@NonNull String query, String path, List<String> attributes, Integer maxResults, Integer page, Map<String, Boolean> params);
	
	/**
	 * <p>fetchEntityListPage.</p>
	 *
	 * @param entityQuery a {@link fr.becpg.api.model.RemoteEntity} object
	 * @param query a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param attributes a {@link java.util.List} object
	 * @param maxResults a {@link java.lang.Integer} object
	 * @param page a {@link java.lang.Integer} object
	 * @return a {@link reactor.core.publisher.Mono} object
	 */
	Mono<RemoteEntityList> fetchEntityListPage(RemoteEntity entityQuery, String query, String path, List<String> attributes, Integer maxResults,
			Integer page);

	/**
	 * <p>fetchEntityListAllPages.</p>
	 *
	 * @param query a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param attributes a {@link java.util.List} object
	 * @param maxResults a {@link java.lang.Integer} object
	 * @return a {@link reactor.core.publisher.Flux} object
	 */
	Flux<RemoteEntityList> fetchEntityListAllPages(@NonNull String query, String path, List<String> attributes, Integer maxResults);

        /**
         * <p>fetchEntityListAllPages.</p>
         *
         * @param query a {@link java.lang.String} object
         * @param path a {@link java.lang.String} object
         * @param attributes a {@link java.util.List} object
         * @param maxResults a {@link java.lang.Integer} object
         * @param params a {@link java.util.Map} object
         * @return a {@link reactor.core.publisher.Flux} object
         */
        Flux<RemoteEntityList> fetchEntityListAllPages(@NonNull String query, String path, List<String> attributes, Integer maxResults, Map<String, Boolean> params);
	
	/**
	 * <p>fetchEntityListAllPages.</p>
	 *
	 * @param entityQuery a {@link fr.becpg.api.model.RemoteEntity} object
	 * @param query a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param attributes a {@link java.util.List} object
	 * @param maxResults a {@link java.lang.Integer} object
	 * @return a {@link reactor.core.publisher.Flux} object
	 */
	Flux<RemoteEntityList> fetchEntityListAllPages(RemoteEntity entityQuery, String query, String path, List<String> attributes, Integer maxResults);
	
	/**
	 * <p>fetchEntityListAllPages.</p>
	 *
	 * @param entityQuery a {@link fr.becpg.api.model.RemoteEntity} object
	 * @return a {@link reactor.core.publisher.Flux} object
	 */
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
	 * @param params a map of strings to objects representing the parameters to pass to the API
	 * @return a {@link fr.becpg.api.model.RemoteEntity} object
	 */
	RemoteEntity get(String id, List<String> attributes, List<String> datalists, Map<String, Object> params);

	/**
	 * <p>fetchEntity.</p>
	 *
	 * @param id a {@link java.lang.String} object
	 * @param attributes a {@link java.util.List} object
	 * @param datalists a {@link java.util.List} object
	 * @param params a {@link java.util.Map} object
	 * @return a {@link reactor.core.publisher.Mono} object
	 */
	Mono<RemoteEntityRef> fetchEntity(String id, List<String> attributes, List<String> datalists, Map<String, Object> params);

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
	 * @param params a map of strings to objects representing the parameters to pass to the API
	 * @return a {@link fr.becpg.api.model.RemoteEntitySchema} object
	 */
	RemoteEntitySchema getSchema(String id, List<String> attributes, List<String> datalists, Map<String, Object> params);

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
	 * @param params a map of strings to objects representing the parameters to pass to the API  (Not supported Yet)
	 * @return a {@link fr.becpg.api.model.RemoteEntitySchema} object
	 */
	RemoteEntitySchema getSchemaForType(String type, List<String> attributes, List<String> datalists, Map<String, Object> params);

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

	/**
	 * <p>updateEntity.</p>
	 *
	 * @param entity a {@link fr.becpg.api.model.RemoteEntity} object
	 * @param createversion a boolean
	 * @param majorVersion a boolean
	 * @param versionDescription a {@link java.lang.String} object
	 * @return a {@link reactor.core.publisher.Mono} object
	 */
	Mono<RemoteAPIResponse> updateEntity(RemoteEntity entity, boolean createversion, boolean majorVersion, String versionDescription);

	/**
	 * <p>formulate.</p>
	 * Formulates the entity. Available from remote API version 2.1.
	 *
	 * @param id the entity id or nodeRef
	 * @return the response of the repository, or <code>null</code> when the formulation did not complete
	 */
	RemoteAPIResponse formulate(String id);

	/**
	 * <p>formulateEntity.</p>
	 * Formulates the entity. Available from remote API version 2.1.
	 *
	 * @param id the entity id or nodeRef
	 * @return the response of the repository, empty when the formulation did not complete
	 */
	Mono<RemoteAPIResponse> formulateEntity(String id);

	/**
	 * <p>branch.</p>
	 * Creates a branch of the entity. Available from remote API version 2.1.
	 *
	 * @param id the entity id or nodeRef
	 * @param destFolderId the id or nodeRef of the folder of the branch, <code>null</code> to create it next to the entity
	 * @return the response of the repository, whose id is the one of the branch
	 */
	RemoteAPIResponse branch(String id, String destFolderId);

	/**
	 * <p>branchEntity.</p>
	 * Creates a branch of the entity. Available from remote API version 2.1.
	 *
	 * @param id the entity id or nodeRef
	 * @param destFolderId the id or nodeRef of the folder of the branch, <code>null</code> to create it next to the entity
	 * @return the response of the repository, whose id is the one of the branch
	 */
	Mono<RemoteAPIResponse> branchEntity(String id, String destFolderId);

	/**
	 * <p>merge.</p>
	 * Merges a branch into the entity it was created from. Available from remote API version 2.1.
	 *
	 * @param id the branch id or nodeRef
	 * @param branchToId the id or nodeRef of the entity the branch is merged into, mandatory
	 * @param majorVersion <code>true</code> to create a major version, <code>false</code> for a minor one
	 * @param description the description of the new version, may be <code>null</code>
	 * @param impactWused <code>true</code> to also create a version of the entities using the merged one
	 * @return the response of the repository, whose id is the one of the merged entity
	 */
	RemoteAPIResponse merge(String id, String branchToId, boolean majorVersion, String description, boolean impactWused);

	/**
	 * <p>mergeEntity.</p>
	 * Merges a branch into the entity it was created from. Available from remote API version 2.1.
	 *
	 * @param id the branch id or nodeRef
	 * @param branchToId the id or nodeRef of the entity the branch is merged into, mandatory
	 * @param majorVersion <code>true</code> to create a major version, <code>false</code> for a minor one
	 * @param description the description of the new version, may be <code>null</code>
	 * @param impactWused <code>true</code> to also create a version of the entities using the merged one
	 * @return the response of the repository, whose id is the one of the merged entity
	 */
	Mono<RemoteAPIResponse> mergeEntity(String id, String branchToId, boolean majorVersion, String description, boolean impactWused);

	/**
	 * <p>delete.</p>
	 * Deletes the entity. Available from remote API version 1.0.
	 *
	 * @param id the entity id or nodeRef
	 * @return the response of the repository
	 */
	RemoteAPIResponse delete(String id);

	/**
	 * <p>deleteEntity.</p>
	 * Deletes the entity. Available from remote API version 1.0.
	 *
	 * @param id the entity id or nodeRef
	 * @return the response of the repository
	 */
	Mono<RemoteAPIResponse> deleteEntity(String id);

}
