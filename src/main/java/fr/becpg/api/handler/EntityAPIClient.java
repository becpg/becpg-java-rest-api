package fr.becpg.api.handler;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import fr.becpg.api.model.RemoteEntity;
import fr.becpg.api.model.RemoteEntityList;
import fr.becpg.api.model.RemoteEntityRef;

/**
 *
 *  @author matthieu
 *  <url>/becpg/remote/entity/list?path={path}</url>
 *  <url>/becpg/remote/entity/list?query={query}&maxResults={maxResults}</url>
 *  <url>/becpg/remote/entity/list?query={query}&maxResults={maxResults}&fields={fields}</url>
 *  <url>/becpg/remote/entity?nodRef={nodeRef}&lists={lists}&fields={fields}</url>
 */
@Component
public class EntityAPIClient extends AbstractAPIClient implements EntityAPI  {

	@Override
	public List<RemoteEntityRef> list(@NonNull String query) {
	  RemoteEntityList entityList = webClient
				.get().uri(uriBuilder -> uriBuilder.path("/entity/list").queryParam(PARAM_FORMAT, FORMAT_JSON)
						.queryParam(PARAM_QUERY, query).build())
				.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(RemoteEntityList.class).block();

		return entityList !=null ? entityList.getEntities() : null;

	}

	@Override
	public List<RemoteEntityRef> list(@NonNull String query, List<String> attributes, int maxResults) {
		RemoteEntityList entityList = webClient.get()
				.uri(uriBuilder -> uriBuilder.path("/entity/list").queryParam(PARAM_FORMAT, FORMAT_JSON).queryParam(PARAM_QUERY, query)
						.queryParam(PARAM_MAX_RESULTS, maxResults).queryParam(PARAM_FIELDS, buildFieldsParam(attributes)).build())
				.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(RemoteEntityList.class).block();
		
		return entityList !=null ? entityList.getEntities() : null;
	}

	@Override
	public RemoteEntity get(String id) {
		RemoteEntityRef entityRef =  webClient.get().uri(
				uriBuilder -> uriBuilder.path("/entity").queryParam(PARAM_FORMAT, FORMAT_JSON).queryParam(PARAM_NODEREF, buildNodeRefParam(id)).build())
				.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(RemoteEntityRef.class).block();

		return entityRef!=null ? entityRef.getEntity() : null;	
	}

	@Override
	public RemoteEntity get(String id, List<String> attributes, List<String> datalists, Map<String, Boolean> params) {
		RemoteEntityRef entityRef =  webClient.get()
				.uri(uriBuilder -> uriBuilder.path("/entity").queryParam(PARAM_FORMAT, FORMAT_JSON)
						.queryParam(PARAM_NODEREF, buildNodeRefParam(id)).queryParam(PARAM_FIELDS, buildFieldsParam(attributes))
						.queryParam(PARAM_LISTS, buildFieldsParam(datalists)).queryParam(PARAM_PARAMS, buildJsonParams(params)).build())
				.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(RemoteEntityRef.class).block();
		
		return entityRef!=null ? entityRef.getEntity() : null;	
	}

	@Override
	public void update(RemoteEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(RemoteEntity entity, boolean createversion, boolean majorVersion, String versionDescription) {
		// TODO Auto-generated method stub

	}


}
