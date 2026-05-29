package fr.becpg.api.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;

import fr.becpg.api.model.ChannelAPIModel;
import fr.becpg.api.model.RemoteAPIError;
import fr.becpg.api.model.RemoteAPIException;
import fr.becpg.api.model.RemoteAPIResponse;
import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteEntityList;
import fr.becpg.api.model.RemoteEntityRef;
import fr.becpg.api.security.EnableAuthConfiguration;
import reactor.core.publisher.Mono;

/**
 * <p>ChannelAPIClient class.</p>
 *
 * @version $Id: $Id
 * @author matthieu
 */
@Component
@EnableAuthConfiguration
public class ChannelAPIClient extends AbstractAPIClient implements ChannelAPI {

	private static final String PARAM_CHANNELID = "channelId";

	/** {@inheritDoc} */
	@Override
	public List<RemoteEntityRef> list(@NonNull String channelId) {
		RemoteEntityList entityList = webClient().get()
				.uri(uriBuilder -> uriBuilder
						.path("/channel/list").queryParam(PARAM_FORMAT, FORMAT_JSON).queryParam(PARAM_CHANNELID, channelId).build())
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::isError,
						response -> response.bodyToMono(RemoteAPIError.class).flatMap(error -> Mono.error(new RemoteAPIException(error))))
				.bodyToMono(RemoteEntityList.class).block();

		return entityList != null ? entityList.getEntities() : null;

	}

	/** {@inheritDoc} */
	@Override
	public List<RemoteEntityRef> list(@NonNull String channelId, List<String> attributes, int maxResults) {
		RemoteEntityList entityList = webClient().get()
				.uri(uriBuilder -> uriBuilder.path("/channel/list").queryParam(PARAM_FORMAT, FORMAT_JSON).queryParam(PARAM_CHANNELID, channelId)
						.queryParam(PARAM_MAX_RESULTS, maxResults).queryParam(PARAM_FIELDS,VAR_FIELDS).build( buildFieldsParam(attributes)))
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::isError,
						response -> response.bodyToMono(RemoteAPIError.class).flatMap(error -> Mono.error(new RemoteAPIException(error))))
				.bodyToMono(RemoteEntityList.class).block();

		return entityList != null ? entityList.getEntities() : null;
	}

	/** {@inheritDoc} */
	@Override
	public RemoteEntity get(@NonNull String channelId) {
		RemoteEntityRef entityRef = webClient().get()
				.uri(uriBuilder -> uriBuilder
						.path("/entity").queryParam(PARAM_FORMAT, FORMAT_JSON).queryParam(PARAM_QUERY,VAR_QUERY).build( buildQuery(channelId)))
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(HttpStatusCode::isError,
						response -> response.bodyToMono(RemoteAPIError.class).flatMap(error -> Mono.error(new RemoteAPIException(error))))
				.bodyToMono(RemoteEntityRef.class).block();

		return entityRef != null ? entityRef.getEntity() : null;
	}

	private String buildQuery(String channelId) {
		return String.format("+TYPE:\"bp:pubChannel\" AND =bp\\:pubChannelId:\"%s\" ", channelId);
	}

	/** {@inheritDoc} */
	@Override
	public RemoteAPIResponse batchStart(@NonNull String channelId, @NonNull String batchId) {
		RemoteEntity entity = new RemoteEntity();
		Map<String, Object> attributes = new HashMap<>();
		attributes.put(ChannelAPIModel.PROP_CHANNEL_BATCHID, batchId);
		entity.setAttributes(attributes);

		return webClient().put()
				.uri(uriBuilder -> uriBuilder
						.path("/channel/batch/start")
						.queryParam(PARAM_FORMAT, FORMAT_JSON)
						.queryParam(PARAM_CHANNELID, channelId)
						.build())
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(new RemoteEntityRef(entity)))
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
				.bodyToMono(RemoteAPIResponse.class)
				.block();
	}

	/** {@inheritDoc} */
	@Override
	public RemoteAPIResponse batchAck(@NonNull String channelId, @NonNull String nodeRef, @NonNull Map<String, Object> attributes) {
		RemoteEntity entity = new RemoteEntity();
		entity.setAttributes(attributes);

		return webClient().post()
				.uri(uriBuilder -> uriBuilder
						.path("/channel/batch/ack")
						.queryParam(PARAM_FORMAT, FORMAT_JSON)
						.queryParam(PARAM_CHANNELID, channelId)
						.queryParam(PARAM_NODEREF, buildNodeRefParam(nodeRef))
						.build())
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(new RemoteEntityRef(entity)))
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
				.bodyToMono(RemoteAPIResponse.class)
				.block();
	}

	/** {@inheritDoc} */
	@Override
	public RemoteAPIResponse batchEnd(@NonNull String channelId, @NonNull Map<String, Object> attributes) {
		RemoteEntity entity = new RemoteEntity();
		entity.setAttributes(attributes);

		return webClient().put()
				.uri(uriBuilder -> uriBuilder
						.path("/channel/batch/end")
						.queryParam(PARAM_FORMAT, FORMAT_JSON)
						.queryParam(PARAM_CHANNELID, channelId)
						.build())
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(new RemoteEntityRef(entity)))
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(HttpStatusCode::isError, response -> handleErrorResponse(response))
				.bodyToMono(RemoteAPIResponse.class)
				.block();
	}

}
