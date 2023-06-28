package fr.becpg.api.handler;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import fr.becpg.api.model.RemoteAPIError;
import fr.becpg.api.model.RemoteAPIException;
import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteEntityList;
import fr.becpg.api.model.RemoteEntityRef;
import reactor.core.publisher.Mono;

/**
 * <p>ChannelAPIClient class.</p>
 *
 * @version $Id: $Id
 * @author matthieu
 */
@Component
public class ChannelAPIClient extends AbstractAPIClient implements ChannelAPI  {

	private static final String PARAM_CHANNELID = "channelId";

	/** {@inheritDoc} */
	@Override
	public List<RemoteEntityRef> list(@NonNull String channelId) {
	  RemoteEntityList entityList = webClient
				.get().uri(uriBuilder -> uriBuilder.path("/channel/list").queryParam(PARAM_FORMAT, FORMAT_JSON)
						.queryParam(PARAM_CHANNELID, channelId).build())
				.accept(MediaType.APPLICATION_JSON).retrieve().onStatus(HttpStatusCode::isError, response -> response.bodyToMono(RemoteAPIError.class) 
                        .flatMap(error -> Mono.error(new RemoteAPIException(error)))) .bodyToMono(RemoteEntityList.class).block();

		return entityList !=null ? entityList.getEntities() : null;

	}

	/** {@inheritDoc} */
	@Override
	public List<RemoteEntityRef> list(@NonNull String channelId, List<String> attributes, int maxResults) {
		RemoteEntityList entityList = webClient.get()
				.uri(uriBuilder -> uriBuilder.path("/channel/list").queryParam(PARAM_FORMAT, FORMAT_JSON).queryParam(PARAM_CHANNELID, channelId)
						.queryParam(PARAM_MAX_RESULTS, maxResults).queryParam(PARAM_FIELDS, buildFieldsParam(attributes)).build())
				.accept(MediaType.APPLICATION_JSON).retrieve().onStatus(HttpStatusCode::isError, response -> response.bodyToMono(RemoteAPIError.class) 
                        .flatMap(error -> Mono.error(new RemoteAPIException(error)))) .bodyToMono(RemoteEntityList.class).block();
		
		return entityList !=null ? entityList.getEntities() : null;
	}

	
	/** {@inheritDoc} */
	@Override
	public RemoteEntity get(String channelId) {
		RemoteEntityRef entityRef = webClient
				.get().uri(uriBuilder -> uriBuilder.path("/entity").queryParam(PARAM_FORMAT, FORMAT_JSON)
						.queryParam(PARAM_QUERY, buildQuery(channelId)).build())
				.accept(MediaType.APPLICATION_JSON).retrieve().onStatus(HttpStatusCode::isError, response -> response.bodyToMono(RemoteAPIError.class) 
                        .flatMap(error -> Mono.error(new RemoteAPIException(error)))) .bodyToMono(RemoteEntityRef.class).block();

		return entityRef != null ?  entityRef.getEntity() : null;
	}

	private String buildQuery(String channelId) {
		return String.format("+TYPE:\"bp:pubChannel\" AND =bp\\:pubChannelId:\"%s\" ", channelId);
	}



}
