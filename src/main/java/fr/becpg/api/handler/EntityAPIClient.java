package fr.becpg.api.handler;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.becpg.api.BecpgRestApiConfiguration;
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
public class EntityAPIClient implements EntityApi {

	private static final String FORMAT_JSON = "json";
	private static final String PARAM_FORMAT = "format";
	private static final String PARAM_QUERY = "query";
	private static final String PARAM_MAX_RESULTS = "maxResults";
	private static final String PARAM_FIELDS = "fields";
	private static final String PARAM_NODEREF = "nodeRef";
	private static final String PARAM_LISTS = "lists";
	private static final String PARAM_PARAMS = "params";

	@Autowired
	private BecpgRestApiConfiguration apiConfiguration;
	private WebClient webClient;

	@PostConstruct
	private void postConstruct() {

		String baseUrl = apiConfiguration.getContentServiceUrl() + "/alfresco/service/becpg/remote";
		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
		factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT);

		this.webClient = WebClient.builder()
				.codecs(configurer -> configurer
					      .defaultCodecs()
					      .maxInMemorySize(16 * 1024 * 1024))
				.defaultHeaders(header -> header.setBasicAuth(apiConfiguration.getBasicAuthUsername(), apiConfiguration.getBasicAuthPassword()))
				.baseUrl(baseUrl).build();

	}

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

	private String buildFieldsParam(List<String> fields) {
		if (fields != null) {
			return String.join(",", fields);
		}
		return null;
	}

	private String buildNodeRefParam(String id) {
		if ((id != null) && !id.contains(":/")) {
			return String.format("workspace://SpacesStore/%s", id);
		}

		return id;
	}

	private String buildJsonParams(Map<String, Boolean> params) {
		if (params != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				return objectMapper.writeValueAsString(params);
			} catch (JsonProcessingException e) {
				//TODO remove that
				e.printStackTrace();
			}
		}
		return null;
	}

}
