package fr.becpg.api.handler;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.becpg.api.BecpgRestApiConfiguration;

public abstract class AbstractAPIClient {

	protected static final String FORMAT_JSON = "json";
	protected static final String PARAM_FORMAT = "format";
	protected static final String PARAM_QUERY = "query";
	protected static final String PARAM_MAX_RESULTS = "maxResults";
	protected static final String PARAM_FIELDS = "fields";
	protected static final String PARAM_NODEREF = "nodeRef";
	protected static final String PARAM_LISTS = "lists";
	protected static final String PARAM_PARAMS = "params";

	@Autowired
	protected BecpgRestApiConfiguration apiConfiguration;
	protected WebClient webClient;

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
	

	protected String buildFieldsParam(List<String> fields) {
		if (fields != null) {
			return String.join(",", fields);
		}
		return null;
	}

	protected String buildNodeRefParam(String id) {
		if ((id != null) && !id.contains(":/")) {
			return String.format("workspace://SpacesStore/%s", id);
		}

		return id;
	}

	protected String buildJsonParams(Map<String, Boolean> params) {
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
