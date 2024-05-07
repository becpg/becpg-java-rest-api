package fr.becpg.api.handler;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
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


	/** {@inheritDoc} */
	@Override
	public List<RemoteEntityRef> list(@NonNull String query) {
		RemoteEntityList entityList =  fetchEntityList(query, null, null, null).block();

		return entityList != null ? entityList.getEntities() : null;

	}

	/** {@inheritDoc} */
	@Override
	public List<RemoteEntityRef> list(@NonNull String query, List<String> attributes, int maxResults) {
		RemoteEntityList entityList = fetchEntityList(query, null, attributes, maxResults).block();

		return entityList != null ? entityList.getEntities() : null;
	}

	/** {@inheritDoc} */
	@Override
	public List<RemoteEntityRef> listByPath(@NonNull String query, @NonNull String path, List<String> attributes, int maxResults) {
		RemoteEntityList entityList = fetchEntityList(query, path, attributes, maxResults).block();

		return entityList != null ? entityList.getEntities() : null;
	}

	
	public Mono<RemoteEntityList> fetchEntityList(@NonNull String query, String path ,List<String> attributes, Integer maxResults) {
	        return webClient().get()
	                .uri(uriBuilder -> uriBuilder.path(REMOTE_ENTITY_URL+"/list")
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
	public RemoteEntity get(String id) {
		RemoteEntityRef entityRef = fetchEntity(id, null, null, null).block();

		return entityRef != null ? entityRef.getEntity() : null;
	}

	/** {@inheritDoc} */
	@Override
	public RemoteEntity get(String id, List<String> attributes, List<String> datalists, Map<String, Boolean> params) {
		RemoteEntityRef entityRef = fetchEntity(id, attributes, datalists, params).block();

		return entityRef != null ? entityRef.getEntity() : null;
	}

	
	@Override
	public Mono<RemoteEntityRef> fetchEntity(String id, List<String> attributes, List<String> datalists, Map<String, Boolean> params) {
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
	public RemoteEntitySchema getSchema(String id, List<String> attributes, List<String> datalists, Map<String, Boolean> params) {
		return webClient().get()
				.uri(uriBuilder -> uriBuilder.path(REMOTE_ENTITY_URL).queryParam(PARAM_FORMAT, FORMAT_JSON_SCHEMA)
						.queryParam(PARAM_NODEREF, buildNodeRefParam(id)).queryParam(PARAM_FIELDS,VAR_FIELDS)
						.queryParam(PARAM_LISTS, VAR_LISTS).queryParams(buildJsonParams(params)).build( buildFieldsParam(attributes), buildFieldsParam(datalists)))
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
				.bodyToMono(RemoteEntitySchema.class).block();
	}

	@Override
	public RemoteEntitySchema getSchemaForType(String type) {
		return webClient().get()
				.uri(uriBuilder -> uriBuilder.path("/dictionary").queryParam(PARAM_FORMAT, FORMAT_JSON_SCHEMA).queryParam(PARAM_TYPE, type).build())
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
				.bodyToMono(RemoteEntitySchema.class).block();
	}

	@Override
	public RemoteEntitySchema getSchemaForType(String type, List<String> attributes, List<String> datalists, Map<String, Boolean> params) {
		return webClient().get()
				.uri(uriBuilder -> uriBuilder.path("/dictionary").queryParam(PARAM_FORMAT, FORMAT_JSON_SCHEMA).queryParam(PARAM_TYPE, type)
						.queryParam(PARAM_FIELDS,VAR_FIELDS).queryParam(PARAM_LISTS, VAR_LISTS)
						.queryParams(buildJsonParams(params)).build( buildFieldsParam(attributes),buildFieldsParam(datalists) ))
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
				.bodyToMono(RemoteEntitySchema.class).block();
	}

}
