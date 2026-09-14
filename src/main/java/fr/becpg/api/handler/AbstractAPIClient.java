package fr.becpg.api.handler;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.becpg.api.BecpgRestApiConfiguration;
import fr.becpg.api.helper.CompressParamHelper;
import fr.becpg.api.model.RemoteAPIError;
import fr.becpg.api.model.RemoteAPIError.RemoteAPIErrorStatus;
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

	/** Constant <code>PARAM_PAGE="page"</code> */
	protected static final String PARAM_PAGE = "page";

	/** Constant <code>VAR_FIELDS="{fields}"</code> */
	protected static final String VAR_FIELDS = "{fields}";

	/** Constant <code>VAR_QUERY="{query}"</code> */
	protected static final String VAR_QUERY = "{query}";

	/** Constant <code>VAR_LISTS="{lists}"</code> */
	protected static final String VAR_LISTS = "{lists}";

	@Autowired
	protected BecpgRestApiConfiguration apiConfiguration;

	@Autowired
	@Qualifier("remoteWebClient")
	protected WebClient webClient;
	
	public WebClient webClient() {
		return webClient;
	}

	private static final int MAX_ERROR_BODY_LENGTH = 512;

	private static final ObjectMapper errorMapper = new ObjectMapper();

	/**
	 * Handle remote errors
	 * <p>
	 * The body is read as text and only then parsed: a repository that does not know the webscript answers with its HTML error page, and the caller
	 * still needs to see the status code (404) rather than a parsing failure.
	 *
	 * @param response a {@link org.springframework.web.reactive.function.client.ClientResponse} object
	 * @return a {@link reactor.core.publisher.Mono} object
	 */
	protected Mono<RemoteAPIException> handleErrorResponse(ClientResponse response) {
		HttpStatusCode statusCode = response.statusCode();
		return response.bodyToMono(String.class).defaultIfEmpty("").map(body -> toRemoteAPIError(statusCode, body))
				.flatMap(error -> Mono.error(new RemoteAPIException(error)));
	}

	private RemoteAPIError toRemoteAPIError(HttpStatusCode statusCode, String body) {
		RemoteAPIError error = null;

		String trimmedBody = body.trim();
		if (trimmedBody.startsWith("{")) {
			try {
				error = errorMapper.readValue(trimmedBody, RemoteAPIError.class);
			} catch (Exception e) {
				// Not a remote API error, the raw body is reported instead
				error = null;
			}
		}

		if (error == null) {
			error = new RemoteAPIError();
			if (!trimmedBody.isEmpty()) {
				error.setMessage(trimmedBody.length() > MAX_ERROR_BODY_LENGTH ? trimmedBody.substring(0, MAX_ERROR_BODY_LENGTH) + "..." : trimmedBody);
			}
		}

		if (error.getStatus() == null) {
			RemoteAPIErrorStatus status = new RemoteAPIErrorStatus();
			status.setCode(String.valueOf(statusCode.value()));
			status.setDescription(statusCode.toString());
			error.setStatus(status);
		}

		return error;
	}

	/**
	 * <p>buildFieldsParam.</p>
	 *
	 * @param fields a {@link java.util.List} object
	 * @return a {@link java.lang.String} object
	 */
	protected String buildFieldsParam(List<String> fields) {
		if ((fields != null) && !fields.isEmpty()) {
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
	 * @param <T> a T class
	 */
	protected <T> MultiValueMap<String, String> buildJsonParams(Map<String, T> params) {

		MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();

		if ((params != null) && !params.isEmpty()) {
			for (Entry<String, T> entry : params.entrySet()) {
				paramsMap.put(JSON_PARAM + entry.getKey(), List.of(entry.getValue() == null ? null : entry.getValue().toString()));
			}
		}

		return paramsMap;
	}

}
