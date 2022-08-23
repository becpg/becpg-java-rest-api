package fr.becpg.api.handler;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import fr.becpg.api.model.RemoteAPIError;
import fr.becpg.api.model.RemoteAPIException;
import fr.becpg.api.model.RemoteEntityList;
import fr.becpg.api.model.RemoteEntityRef;
import reactor.core.publisher.Mono;

/**
 *
 *  @author matthieu
 *  <url>/becpg/remote/channel/list?channelId={id}</url>
 */
@Component
public class ChannelAPIClient extends AbstractAPIClient implements ChannelAPI  {

	private static final String PARAM_CHANNELID = "channelId";

	@Override
	public List<RemoteEntityRef> list(@NonNull String channelId) {
	  RemoteEntityList entityList = webClient
				.get().uri(uriBuilder -> uriBuilder.path("/channel/list").queryParam(PARAM_FORMAT, FORMAT_JSON)
						.queryParam(PARAM_CHANNELID, channelId).build())
				.accept(MediaType.APPLICATION_JSON).retrieve().onStatus(HttpStatus::isError, response -> response.bodyToMono(RemoteAPIError.class) 
                        .flatMap(error -> Mono.error(new RemoteAPIException(error)))) .bodyToMono(RemoteEntityList.class).block();

		return entityList !=null ? entityList.getEntities() : null;

	}

	@Override
	public List<RemoteEntityRef> list(@NonNull String channelId, List<String> attributes, int maxResults) {
		RemoteEntityList entityList = webClient.get()
				.uri(uriBuilder -> uriBuilder.path("/channel/list").queryParam(PARAM_FORMAT, FORMAT_JSON).queryParam(PARAM_CHANNELID, channelId)
						.queryParam(PARAM_MAX_RESULTS, maxResults).queryParam(PARAM_FIELDS, buildFieldsParam(attributes)).build())
				.accept(MediaType.APPLICATION_JSON).retrieve().onStatus(HttpStatus::isError, response -> response.bodyToMono(RemoteAPIError.class) 
                        .flatMap(error -> Mono.error(new RemoteAPIException(error)))) .bodyToMono(RemoteEntityList.class).block();
		
		return entityList !=null ? entityList.getEntities() : null;
	}



}
