package fr.becpg.api.handler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;

import fr.becpg.api.model.RemoteAPIError;
import fr.becpg.api.model.RemoteAPIException;
import fr.becpg.api.model.RemoteAPIResponse;
import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteEntityList;
import fr.becpg.api.model.RemoteEntityRef;
import fr.becpg.api.model.RemoteEntitySchema;
import fr.becpg.api.security.EnableAuthConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <p>EntityAPIClient class.</p>
 *
 * @version $Id: $Id
 * @author matthieu
 */
@Component
@EnableAuthConfiguration
public class EntityAPIClient extends AbstractAPIClient implements EntityAPI {

	private static final String PARAM_TYPE = "type";
	
	private static final String REMOTE_ENTITY_URL = "/entity";

	private static final String REMOTE_ENTITY_LIST_URL = REMOTE_ENTITY_URL + "/list";

	private static final String REMOTE_FORMULATE_URL = "/formulate";

	private static final String REMOTE_BRANCH_URL = "/branch";

	private static final String REMOTE_MERGE_URL = "/merge";

	private static final String PARAM_DEST_NODEREF = "destNodeRef";

	private static final String PARAM_BRANCH_TO_NODEREF = "branchToNodeRef";

	private static final String PARAM_IMPACT_WUSED = "impactWused";

	private static final String PARAM_DESCRIPTION = "description";


	/** {@inheritDoc} */
	@Override
	public List<RemoteEntityRef> list(@NonNull String query) {
		RemoteEntityList entityList =  fetchEntityList(query, null, null, null).block();

		return entityList != null && entityList.getEntities() != null ? entityList.getEntities() : Collections.emptyList();

	}

	/** {@inheritDoc} */
	@Override
	public List<RemoteEntityRef> list(@NonNull String query, List<String> attributes, int maxResults) {
	    if (maxResults == -1) {
	        return fetchEntityListAllPages(query, null, attributes, maxResults)
	            .flatMapIterable(RemoteEntityList::getEntities)
	            .collectList()
	            .block();
	    }
	    RemoteEntityList entityList =  fetchEntityList(query, null, attributes, maxResults).block();
		return entityList != null && entityList.getEntities() != null ? entityList.getEntities() : Collections.emptyList();
	}

	/** {@inheritDoc} */
	@Override
	public List<RemoteEntityRef> list(@NonNull String query, List<String> attributes, int maxResults, Map<String, Boolean> params) {
	    if (maxResults == -1) {
	        return fetchEntityListAllPages(query, null, attributes, maxResults, params)
	            .flatMapIterable(RemoteEntityList::getEntities)
	            .collectList()
	            .block();
	    }
	    RemoteEntityList entityList =  fetchEntityList(query, null, attributes, maxResults, params).block();
		return entityList != null && entityList.getEntities() != null ? entityList.getEntities() : Collections.emptyList();
	}
	
	/** {@inheritDoc} */
	@Override
	public List<RemoteEntityRef> list(RemoteEntity entityQuery, String query, List<String> attributes, int maxResults) {
	    if (maxResults == -1) {
	        return fetchEntityListAllPages(entityQuery, query, null, attributes, maxResults)
	            .flatMapIterable(RemoteEntityList::getEntities)
	            .collectList()
	            .block();
	    }
	    RemoteEntityList entityList =  fetchEntityList(query, null, attributes, maxResults).block();
		return entityList != null && entityList.getEntities() != null ? entityList.getEntities() : Collections.emptyList();
	}
	
	/** {@inheritDoc} */
	@Override
	public List<RemoteEntityRef> list(RemoteEntity entityQuery) {
		return list(entityQuery, null, null, -1);
	}

	/** {@inheritDoc} */
	@Override
	public List<RemoteEntityRef> listByPath(@NonNull String query, @NonNull String path, List<String> attributes, int maxResults) {
	    if (maxResults == -1) {
	        return fetchEntityListAllPages(query, path, attributes, maxResults)
	            .flatMapIterable(RemoteEntityList::getEntities)
	            .collectList()
	            .block();
	    }
	    RemoteEntityList entityList =  fetchEntityList(query, path, attributes, maxResults).block();

		return entityList != null && entityList.getEntities() != null ? entityList.getEntities() : Collections.emptyList();
	}

	
	/** {@inheritDoc} */
	@Override
	public Mono<RemoteEntityList> fetchEntityList(@NonNull String query, String path ,List<String> attributes, Integer maxResults) {
        return webClient().get()
                .uri(uriBuilder -> uriBuilder.path(REMOTE_ENTITY_LIST_URL)
                        .queryParam(PARAM_FORMAT, FORMAT_JSON)
                        .queryParam(PARAM_QUERY, VAR_QUERY )           
                        .queryParamIfPresent(PARAM_PATH, Optional.ofNullable(path))
                        .queryParamIfPresent(PARAM_MAX_RESULTS, Optional.ofNullable(maxResults))
                        .queryParam(PARAM_FIELDS, VAR_FIELDS)
                        .build(query, buildFieldsParam(attributes)))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
                .bodyToMono(RemoteEntityList.class);
    }

	/** {@inheritDoc} */
	@Override
	public Mono<RemoteEntityList> fetchEntityList(@NonNull String query, String path, List<String> attributes, Integer maxResults, Map<String, Boolean> params) {
        return webClient().get()
                .uri(uriBuilder -> uriBuilder.path(REMOTE_ENTITY_LIST_URL)
                        .queryParam(PARAM_FORMAT, FORMAT_JSON)
                        .queryParam(PARAM_QUERY, VAR_QUERY )           
                        .queryParamIfPresent(PARAM_PATH, Optional.ofNullable(path))
                        .queryParamIfPresent(PARAM_MAX_RESULTS, Optional.ofNullable(maxResults))
                        .queryParam(PARAM_FIELDS, VAR_FIELDS)
                        .queryParams(buildJsonParams(params))
                        .build(query, buildFieldsParam(attributes)))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
                .bodyToMono(RemoteEntityList.class);
	}
	
	/** {@inheritDoc} */
	@Override
	public Mono<RemoteEntityList> fetchEntityListPage(@NonNull String query, String path, List<String> attributes, Integer maxResults, Integer page) {
        return webClient().get()
                .uri(uriBuilder -> uriBuilder.path(REMOTE_ENTITY_LIST_URL)
                        .queryParam(PARAM_FORMAT, FORMAT_JSON)
                        .queryParam(PARAM_QUERY, VAR_QUERY )           
                        .queryParamIfPresent(PARAM_PATH, Optional.ofNullable(path))
                        .queryParamIfPresent(PARAM_MAX_RESULTS, Optional.ofNullable(maxResults))
                        .queryParamIfPresent(PARAM_PAGE, Optional.ofNullable(page))
                        .queryParam(PARAM_FIELDS, VAR_FIELDS)
                        .build(query, buildFieldsParam(attributes)))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
                .bodyToMono(RemoteEntityList.class);
    }

	/** {@inheritDoc} */
	@Override
	public Mono<RemoteEntityList> fetchEntityListPage(@NonNull String query, String path, List<String> attributes, Integer maxResults, Integer page, Map<String, Boolean> params) {
        return webClient().get()
                .uri(uriBuilder -> uriBuilder.path(REMOTE_ENTITY_LIST_URL)
                        .queryParam(PARAM_FORMAT, FORMAT_JSON)
                        .queryParam(PARAM_QUERY, VAR_QUERY )           
                        .queryParamIfPresent(PARAM_PATH, Optional.ofNullable(path))
                        .queryParamIfPresent(PARAM_MAX_RESULTS, Optional.ofNullable(maxResults))
                        .queryParamIfPresent(PARAM_PAGE, Optional.ofNullable(page))
                        .queryParam(PARAM_FIELDS, VAR_FIELDS)
                        .queryParams(buildJsonParams(params))
                        .build(query, buildFieldsParam(attributes)))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
                .bodyToMono(RemoteEntityList.class);
    }
	
	/** {@inheritDoc} */
	@Override
	public Mono<RemoteEntityList> fetchEntityListPage(RemoteEntity entityQuery, String query, String path, List<String> attributes, Integer maxResults, Integer page) {
		return webClient().post()
				.uri(uriBuilder -> uriBuilder.path(REMOTE_ENTITY_LIST_URL)
						.queryParam(PARAM_FORMAT, FORMAT_JSON)
						.queryParam(PARAM_QUERY, VAR_QUERY )           
						.queryParamIfPresent(PARAM_PATH, Optional.ofNullable(path))
						.queryParamIfPresent(PARAM_MAX_RESULTS, Optional.ofNullable(maxResults))
						.queryParamIfPresent(PARAM_PAGE, Optional.ofNullable(page))
						.queryParam(PARAM_FIELDS, VAR_FIELDS)
						.build(query, buildFieldsParam(attributes)))
				.body(BodyInserters.fromValue(entityQuery))
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
				.bodyToMono(RemoteEntityList.class);
	}
	
	/** {@inheritDoc} */
	@Override
	public Flux<RemoteEntityList> fetchEntityListAllPages(@NonNull String query, String path, List<String> attributes, Integer maxResults) {
		AtomicInteger pageNumber = new AtomicInteger(1);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return fetchEntityListPage(query, path, attributes, maxResults, pageNumber.get())
			.expand(remoteEntityList -> {
				if (remoteEntityList.hasMoreItems()) {
					SecurityContextHolder.getContext().setAuthentication(authentication);
					return fetchEntityListPage(query, path, attributes, maxResults, pageNumber.addAndGet(1));
				}
				return Mono.empty();
			});
	}

	/** {@inheritDoc} */
	@Override
	public Flux<RemoteEntityList> fetchEntityListAllPages(@NonNull String query, String path, List<String> attributes, Integer maxResults, Map<String, Boolean> params) {
		AtomicInteger pageNumber = new AtomicInteger(1);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return fetchEntityListPage(query, path, attributes, maxResults, pageNumber.get(), params)
			.expand(remoteEntityList -> {
				if (remoteEntityList.hasMoreItems()) {
					SecurityContextHolder.getContext().setAuthentication(authentication);
					return fetchEntityListPage(query, path, attributes, maxResults, pageNumber.addAndGet(1), params);
				}
				return Mono.empty();
			});
	}
	
	/** {@inheritDoc} */
	@Override
	public Flux<RemoteEntityList> fetchEntityListAllPages(RemoteEntity entityQuery, String query, String path, List<String> attributes, Integer maxResults) {
		AtomicInteger pageNumber = new AtomicInteger(1);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return fetchEntityListPage(entityQuery, query, path, attributes, maxResults, pageNumber.get())
				.expand(remoteEntityList -> {
					if (remoteEntityList.hasMoreItems()) {
						SecurityContextHolder.getContext().setAuthentication(authentication);
						return fetchEntityListPage(entityQuery, query, path, attributes, maxResults, pageNumber.addAndGet(1));
					}
					return Mono.empty();
				});
	}
	
	/** {@inheritDoc} */
	@Override
	public Flux<RemoteEntityList> fetchEntityListAllPages(RemoteEntity entityQuery) {
		return fetchEntityListAllPages(entityQuery, null, null, null, -1);
	}
	  

	/** {@inheritDoc} */
	@Override
	public RemoteEntity get(String id) {
		RemoteEntityRef entityRef = fetchEntity(id, null, null, null).block();

		return entityRef != null ? entityRef.getEntity() : null;
	}

	/** {@inheritDoc} */
	@Override
	public RemoteEntity get(String id, List<String> attributes, List<String> datalists, Map<String, Object> params) {
		RemoteEntityRef entityRef = fetchEntity(id, attributes, datalists, params).block();

		return entityRef != null ? entityRef.getEntity() : null;
	}

	
	/** {@inheritDoc} */
	@Override
	public Mono<RemoteEntityRef> fetchEntity(String id, List<String> attributes, List<String> datalists, Map<String, Object> params) {
		return webClient().get()
				.uri(uriBuilder -> uriBuilder.path(REMOTE_ENTITY_URL).queryParam(PARAM_FORMAT, FORMAT_JSON).queryParam(PARAM_NODEREF, buildNodeRefParam(id))
						.queryParam(PARAM_FIELDS, VAR_FIELDS)
						.queryParam(PARAM_LISTS,VAR_LISTS)
						.queryParams(buildJsonParams(params)).build(Optional.ofNullable(buildFieldsParam(attributes)).orElse(""),Optional.ofNullable(buildFieldsParam(datalists)).orElse("")))
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
				.bodyToMono(RemoteEntityRef.class);
	}
	
	
	/** {@inheritDoc} */
	@Override
	public RemoteEntity update(RemoteEntity entity) {
		return update(entity, false, false, "");
	}

	/** {@inheritDoc} */
	@Override
	public RemoteEntity update(RemoteEntity entity, boolean createversion, boolean majorVersion, String versionDescription) {		
		RemoteAPIResponse resp = updateEntity(entity, createversion, majorVersion, versionDescription).block();
		if(resp!=null ) {
			entity.setId(resp.getId());
		}
		return entity;

	}
	
	/** {@inheritDoc} */
	@Override
	public Mono<RemoteAPIResponse> updateEntity(RemoteEntity entity, boolean createversion, boolean majorVersion, String versionDescription) {
		
		if (entity.getId() != null && !entity.getId().isBlank()) {
			return webClient().post()
					.uri(uriBuilder -> uriBuilder.path(REMOTE_ENTITY_URL).queryParam(PARAM_FORMAT, FORMAT_JSON)
							.queryParam(PARAM_NODEREF, buildNodeRefParam((entity.getId()))).queryParam(PARAM_CREATE_VERSION, createversion)
							.queryParam(PARAM_MAJOR_VERSION, majorVersion).queryParam(PARAM_VERSION_DESCRIPTION, "{description}").build(versionDescription))

					.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(new RemoteEntityRef(entity)))
					.accept(MediaType.APPLICATION_JSON).retrieve()
					.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
					.bodyToMono(RemoteAPIResponse.class);
		} 
		
		return webClient().put().uri(uriBuilder -> uriBuilder.path(REMOTE_ENTITY_URL).queryParam(PARAM_FORMAT, FORMAT_JSON).build())
					.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(new RemoteEntityRef(entity)))
					.accept(MediaType.APPLICATION_JSON).retrieve()
					.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
					.bodyToMono(RemoteAPIResponse.class);
		

	}
	

	/** {@inheritDoc} */
	@Override
	public String check(String id) {
		return webClient().get().uri(uriBuilder -> uriBuilder.path("/check").queryParam(PARAM_NODEREF, buildNodeRefParam(id)).build()).retrieve()
				.onStatus(HttpStatusCode::isError,
						response -> response.bodyToMono(RemoteAPIError.class).flatMap(error -> Mono.error(new RemoteAPIException(error))))
				.bodyToMono(String.class).block();

	}

	/** {@inheritDoc} */
	@Override
	public RemoteEntitySchema getSchema(String id) {
		return webClient().get()
				.uri(uriBuilder -> uriBuilder
						.path(REMOTE_ENTITY_URL).queryParam(PARAM_FORMAT, FORMAT_JSON_SCHEMA).queryParam(PARAM_NODEREF, buildNodeRefParam(id)).build())
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
				.bodyToMono(RemoteEntitySchema.class).block();

	}

	/** {@inheritDoc} */
	@Override
	public RemoteEntitySchema getSchema(String id, List<String> attributes, List<String> datalists, Map<String, Object> params) {
		return webClient().get()
				.uri(uriBuilder -> uriBuilder.path(REMOTE_ENTITY_URL).queryParam(PARAM_FORMAT, FORMAT_JSON_SCHEMA)
						.queryParam(PARAM_NODEREF, buildNodeRefParam(id)).queryParam(PARAM_FIELDS,VAR_FIELDS)
						.queryParam(PARAM_LISTS, VAR_LISTS).queryParams(buildJsonParams(params)).build( buildFieldsParam(attributes), buildFieldsParam(datalists)))
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
				.bodyToMono(RemoteEntitySchema.class).block();
	}

	/** {@inheritDoc} */
	@Override
	public RemoteEntitySchema getSchemaForType(String type) {
		return webClient().get()
				.uri(uriBuilder -> uriBuilder.path("/dictionary").queryParam(PARAM_FORMAT, FORMAT_JSON_SCHEMA).queryParam(PARAM_TYPE, type).build())
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
				.bodyToMono(RemoteEntitySchema.class).block();
	}

	/** {@inheritDoc} */
	@Override
	public RemoteEntitySchema getSchemaForType(String type, List<String> attributes, List<String> datalists, Map<String, Object> params) {
		return webClient().get()
				.uri(uriBuilder -> uriBuilder.path("/dictionary").queryParam(PARAM_FORMAT, FORMAT_JSON_SCHEMA).queryParam(PARAM_TYPE, type)
						.queryParam(PARAM_FIELDS,VAR_FIELDS).queryParam(PARAM_LISTS, VAR_LISTS)
						.queryParams(buildJsonParams(params)).build( buildFieldsParam(attributes),buildFieldsParam(datalists) ))
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
				.bodyToMono(RemoteEntitySchema.class).block();
	}

	/** {@inheritDoc} */
	@Override
	public RemoteAPIResponse formulate(String id) {
		return formulateEntity(id).block();
	}

	/** {@inheritDoc} */
	@Override
	public Mono<RemoteAPIResponse> formulateEntity(String id) {
		return webClient().get()
				.uri(uriBuilder -> uriBuilder.path(REMOTE_FORMULATE_URL).queryParam(PARAM_FORMAT, FORMAT_JSON)
						.queryParam(PARAM_NODEREF, buildNodeRefParam(id)).build())
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
				.bodyToMono(RemoteAPIResponse.class);
	}

	/** {@inheritDoc} */
	@Override
	public RemoteAPIResponse branch(String id, String destFolderId) {
		return branchEntity(id, destFolderId).block();
	}

	/** {@inheritDoc} */
	@Override
	public Mono<RemoteAPIResponse> branchEntity(String id, String destFolderId) {
		return webClient().post()
				.uri(uriBuilder -> uriBuilder.path(REMOTE_BRANCH_URL).queryParam(PARAM_FORMAT, FORMAT_JSON)
						.queryParam(PARAM_NODEREF, buildNodeRefParam(id))
						.queryParamIfPresent(PARAM_DEST_NODEREF, Optional.ofNullable(buildNodeRefParam(destFolderId))).build())
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
				.bodyToMono(RemoteAPIResponse.class);
	}

	/** {@inheritDoc} */
	@Override
	public RemoteAPIResponse merge(String id, String branchToId, boolean majorVersion, String description, boolean impactWused) {
		return mergeEntity(id, branchToId, majorVersion, description, impactWused).block();
	}

	/** {@inheritDoc} */
	@Override
	public Mono<RemoteAPIResponse> mergeEntity(String id, String branchToId, boolean majorVersion, String description, boolean impactWused) {
		if (branchToId == null) {
			// Reported through the Mono, so that a reactive caller handles it like any other error
			return Mono.error(new NullPointerException("branchToId is mandatory"));
		}
		// The description goes in the body: the repository reads it there without any query string encoding issue
		Map<String, String> body = Map.of(PARAM_DESCRIPTION, description != null ? description : "");

		return webClient().post()
				.uri(uriBuilder -> uriBuilder.path(REMOTE_MERGE_URL).queryParam(PARAM_FORMAT, FORMAT_JSON)
						.queryParam(PARAM_NODEREF, buildNodeRefParam(id))
						.queryParam(PARAM_BRANCH_TO_NODEREF, buildNodeRefParam(branchToId))
						.queryParam(PARAM_MAJOR_VERSION, majorVersion).queryParam(PARAM_IMPACT_WUSED, impactWused).build())
				.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(body))
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
				.bodyToMono(RemoteAPIResponse.class);
	}

	/** {@inheritDoc} */
	@Override
	public RemoteAPIResponse delete(String id) {
		return deleteEntity(id).block();
	}

	/** {@inheritDoc} */
	@Override
	public Mono<RemoteAPIResponse> deleteEntity(String id) {
		return webClient().delete()
				.uri(uriBuilder -> uriBuilder.path(REMOTE_ENTITY_URL).queryParam(PARAM_FORMAT, FORMAT_JSON)
						.queryParam(PARAM_NODEREF, buildNodeRefParam(id)).build())
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
				.bodyToMono(RemoteAPIResponse.class);
	}

}
