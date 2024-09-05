package fr.becpg.api.handler;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import fr.becpg.api.BecpgRestApiConfiguration;
import fr.becpg.api.helper.CompressParamHelper;
import fr.becpg.api.model.RemoteAPIError;
import fr.becpg.api.model.RemoteAPIException;
import reactor.core.publisher.Mono;

/**
 * <p>Abstract AbstractAPIClient class.</p>
 *
 * @author matthieu
 * @version $Id: $Id
 */
public abstract class AbstractAPIClient {

	private static final String JSON_PARAM = "jsonParam";
	/** Constant <code>FORMAT_JSON_SCHEMA="json_schema"</code> */
	protected static final String FORMAT_JSON_SCHEMA = "json_schema";
	/** Constant <code>FORMAT_JSON="json"</code> */
	protected static final String FORMAT_JSON = "json";
	/** Constant <code>PARAM_FORMAT="format"</code> */
	protected static final String PARAM_FORMAT = "format";
	/** Constant <code>PARAM_QUERY="query"</code> */
	protected static final String PARAM_QUERY = "query";
	/** Constant <code>PARAM_PATH="path"</code> */
	protected static final String PARAM_PATH = "path";
	/** Constant <code>PARAM_MAX_RESULTS="maxResults"</code> */
	protected static final String PARAM_MAX_RESULTS = "maxResults";
	/** Constant <code>PARAM_FIELDS="fields"</code> */
	protected static final String PARAM_FIELDS = "fields";
	/** Constant <code>PARAM_NODEREF="nodeRef"</code> */
	protected static final String PARAM_NODEREF = "nodeRef";
	/** Constant <code>PARAM_LISTS="lists"</code> */
	protected static final String PARAM_LISTS = "lists";
	/** Constant <code>PARAM_PARAMS="params"</code> */
	protected static final String PARAM_PARAMS = "params";
	/** Constant <code>PARAM_CREATE_VERSION="createVersion"</code> */
	protected static final String PARAM_CREATE_VERSION = "createVersion";
	/** Constant <code>PARAM_MAJOR_VERSION="majorVersion"</code> */
	protected static final String PARAM_MAJOR_VERSION = "majorVersion";
	/** Constant <code>PARAM_VERSION_DESCRIPTION="versionDescription"</code> */
	protected static final String PARAM_VERSION_DESCRIPTION = "versionDescription";
	
	
	protected static final String VAR_FIELDS = "{fields}";

	protected static final String VAR_QUERY = "{query}";

	protected static final String VAR_LISTS = "{lists}";
	
	@Autowired
	protected BecpgRestApiConfiguration apiConfiguration;

	protected WebClient webClient() {
		return apiConfiguration.webClient();
	}

	
	/**
	 * Handle remote errors
	 * @param response
	 * @return
	 */
    protected Mono<RemoteAPIException> handleErrorResponse(ClientResponse response) {
        return response.bodyToMono(RemoteAPIError.class)
                .flatMap(error -> Mono.error(new RemoteAPIException(error)));
    }

	/**
	 * <p>buildFieldsParam.</p>
	 *
	 * @param fields a {@link java.util.List} object
	 * @return a {@link java.lang.String} object
	 */
	protected String buildFieldsParam(List<String> fields) {
		if (fields != null) {
			return compress(String.join(",", fields));
		}
		return null;
	}

	private String compress(String param) {
		if (apiConfiguration.shouldCompressParam()) {
			return CompressParamHelper.encodeParam(param);
		}

		return param;
	}

	/**
	 * <p>buildNodeRefParam.</p>
	 *
	 * @param id a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	protected String buildNodeRefParam(String id) {
		if ((id != null) && !id.contains(":/")) {
			return String.format("workspace://SpacesStore/%s", id);
		}

		return id;
	}

	/**
	 * <p>buildJsonParams.</p>
	 *
	 * @param params a {@link java.util.Map} object
	 * @return a {@link java.lang.String} object
	 */
	protected <T> MultiValueMap<String, String> buildJsonParams(Map<String, T> params) {

		MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();

		if (params != null && !params.isEmpty()) {
			for (Entry<String, T> entry : params.entrySet()) {
				paramsMap.put(JSON_PARAM + entry.getKey(), List.of(entry.getValue() == null ? null : entry.getValue().toString()));
			}
		}

		return paramsMap;
	}

}
