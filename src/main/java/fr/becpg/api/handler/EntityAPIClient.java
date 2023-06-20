package fr.becpg.api.handler;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;

import fr.becpg.api.model.RemoteAPIError;
import fr.becpg.api.model.RemoteAPIException;
import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteEntityList;
import fr.becpg.api.model.RemoteEntityRef;
import fr.becpg.api.model.RemoteEntitySchema;
import reactor.core.publisher.Mono;

/**
 * <p>EntityAPIClient class.</p>
 *
 * @version $Id: $Id
 * @author matthieu
 */
@Component
public class EntityAPIClient extends AbstractAPIClient implements EntityAPI {

	/** {@inheritDoc} */
	@Override
	public List<RemoteEntityRef> list(@NonNull String query) {
		RemoteEntityList entityList = webClient.get()
				.uri(uriBuilder -> uriBuilder.path("/entity/list").queryParam(PARAM_FORMAT, FORMAT_JSON).queryParam(PARAM_QUERY, query).build())
				.accept(MediaType.APPLICATION_JSON).retrieve().onStatus(HttpStatus::isError, response -> response.bodyToMono(RemoteAPIError.class) 
                        .flatMap(error -> Mono.error(new RemoteAPIException(error)))) .bodyToMono(RemoteEntityList.class).block();

		return entityList != null ? entityList.getEntities() : null;

	}

	/** {@inheritDoc} */
	@Override
	public List<RemoteEntityRef> list(@NonNull String query, List<String> attributes, int maxResults) {
		RemoteEntityList entityList = webClient.get()
				.uri(uriBuilder -> uriBuilder.path("/entity/list").queryParam(PARAM_FORMAT, FORMAT_JSON).queryParam(PARAM_QUERY, query)
						.queryParam(PARAM_MAX_RESULTS, maxResults).queryParam(PARAM_FIELDS, buildFieldsParam(attributes)).build())
				.accept(MediaType.APPLICATION_JSON).retrieve().onStatus(HttpStatus::isError, response -> response.bodyToMono(RemoteAPIError.class) 
                        .flatMap(error -> Mono.error(new RemoteAPIException(error)))) .bodyToMono(RemoteEntityList.class).block();

		return entityList != null ? entityList.getEntities() : null;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public List<RemoteEntityRef> listByPath(@NonNull String query, @NonNull String path, List<String> attributes, int maxResults) {
		RemoteEntityList entityList = webClient.get()
				.uri(uriBuilder -> uriBuilder.path("/entity/list").queryParam(PARAM_FORMAT, FORMAT_JSON).queryParam(PARAM_QUERY, query).queryParam(PARAM_PATH, path)
						.queryParam(PARAM_MAX_RESULTS, maxResults).queryParam(PARAM_FIELDS, buildFieldsParam(attributes)).build())
				.accept(MediaType.APPLICATION_JSON).retrieve().onStatus(HttpStatus::isError, response -> response.bodyToMono(RemoteAPIError.class) 
                        .flatMap(error -> Mono.error(new RemoteAPIException(error)))) .bodyToMono(RemoteEntityList.class).block();

		return entityList != null ? entityList.getEntities() : null;
	}

	/** {@inheritDoc} */
	@Override
	public RemoteEntity get(String id) {
		RemoteEntityRef entityRef = webClient
				.get().uri(uriBuilder -> uriBuilder.path("/entity").queryParam(PARAM_FORMAT, FORMAT_JSON)
						.queryParam(PARAM_NODEREF, buildNodeRefParam(id)).build())
				.accept(MediaType.APPLICATION_JSON).retrieve().onStatus(HttpStatus::isError, response -> response.bodyToMono(RemoteAPIError.class) 
                        .flatMap(error -> Mono.error(new RemoteAPIException(error)))) .bodyToMono(RemoteEntityRef.class).block();

		return entityRef != null ? entityRef.getEntity() : null;
	}

	/** {@inheritDoc} */
	@Override
	public RemoteEntity get(String id, List<String> attributes, List<String> datalists, Map<String, Boolean> params) {
		RemoteEntityRef entityRef = webClient.get()
				.uri(uriBuilder -> uriBuilder.path("/entity").queryParam(PARAM_FORMAT, FORMAT_JSON).queryParam(PARAM_NODEREF, buildNodeRefParam(id))
						.queryParam(PARAM_FIELDS, buildFieldsParam(attributes)).queryParam(PARAM_LISTS, buildFieldsParam(datalists))
						.queryParams(buildJsonParams(params)).build())
				.accept(MediaType.APPLICATION_JSON)
				.retrieve().onStatus(HttpStatus::isError, response -> response.bodyToMono(RemoteAPIError.class) 
                        .flatMap(error -> Mono.error(new RemoteAPIException(error)))) .bodyToMono(RemoteEntityRef.class).block();

		return entityRef != null ? entityRef.getEntity() : null;
	}

	/** {@inheritDoc} */
	@Override
	public RemoteEntity update(RemoteEntity entity) {
		return update(entity, false, false, "");
	}

	/** {@inheritDoc} */
	@Override
	public RemoteEntity update(RemoteEntity entity, boolean createversion, boolean majorVersion, String versionDescription) {

		RemoteEntityRef entityRef;
		if (entity.getId() != null && ! entity.getId().isBlank()) {
			entityRef = webClient.post()
					.uri(uriBuilder -> uriBuilder.path("/entity").queryParam(PARAM_FORMAT, FORMAT_JSON)
							.queryParam(PARAM_NODEREF, buildNodeRefParam((entity.getId())))
							.queryParam(PARAM_CREATE_VERSION, createversion).queryParam(PARAM_MAJOR_VERSION, majorVersion)
							.queryParam(PARAM_VERSION_DESCRIPTION, versionDescription).build())
					        
					.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(new RemoteEntityRef(entity)))
					.accept(MediaType.APPLICATION_JSON).retrieve()
					.onStatus(HttpStatus::isError, response -> response.bodyToMono(RemoteAPIError.class) 
                            .flatMap(error -> Mono.error(new RemoteAPIException(error)))) 
					.bodyToMono(RemoteEntityRef.class).block();

		} else {
			entityRef = webClient.put()
					.uri(uriBuilder -> uriBuilder.path("/entity").queryParam(PARAM_FORMAT, FORMAT_JSON).build())
					.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(new RemoteEntityRef(entity)))
					.accept(MediaType.APPLICATION_JSON).retrieve()
					.onStatus(HttpStatus::isError, response -> response.bodyToMono(RemoteAPIError.class) 
                            .flatMap(error -> Mono.error(new RemoteAPIException(error)))) 
					.bodyToMono(RemoteEntityRef.class).block();
		}

		return entityRef != null ? entityRef.getEntity() : null;

	}

	/** {@inheritDoc} */
	@Override
	public String check(String id) {
		return webClient
				.get().uri(uriBuilder -> uriBuilder.path("/check")
						.queryParam(PARAM_NODEREF, buildNodeRefParam(id)).build())
				.retrieve().onStatus(HttpStatus::isError, response -> response.bodyToMono(RemoteAPIError.class) 
                        .flatMap(error -> Mono.error(new RemoteAPIException(error)))).bodyToMono(String.class).block();

	
	}

	/** {@inheritDoc} */
	@Override
	public RemoteEntitySchema getSchema(String id) {
		return webClient
				.get().uri(uriBuilder -> uriBuilder.path("/entity").queryParam(PARAM_FORMAT, FORMAT_JSON_SCHEMA)
						.queryParam(PARAM_NODEREF, buildNodeRefParam(id)).build())
				.accept(MediaType.APPLICATION_JSON).retrieve().onStatus(HttpStatus::isError, response -> response.bodyToMono(RemoteAPIError.class) 
                        .flatMap(error -> Mono.error(new RemoteAPIException(error)))) .bodyToMono(RemoteEntitySchema.class).block();

	}

	/** {@inheritDoc} */
	@Override
	public RemoteEntitySchema getSchema(String id, List<String> attributes, List<String> datalists, Map<String, Boolean> params) {
		return webClient.get()
		.uri(uriBuilder -> uriBuilder.path("/entity").queryParam(PARAM_FORMAT, FORMAT_JSON_SCHEMA).queryParam(PARAM_NODEREF, buildNodeRefParam(id))
				.queryParam(PARAM_FIELDS, buildFieldsParam(attributes)).queryParam(PARAM_LISTS, buildFieldsParam(datalists))
				.queryParams(buildJsonParams(params)).build())
		.accept(MediaType.APPLICATION_JSON)
		.retrieve().onStatus(HttpStatus::isError, response -> response.bodyToMono(RemoteAPIError.class) 
                .flatMap(error -> Mono.error(new RemoteAPIException(error)))) .bodyToMono(RemoteEntitySchema.class).block();
	}

}
