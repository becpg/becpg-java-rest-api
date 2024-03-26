package fr.becpg.api.handler;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import fr.becpg.api.BecpgRestApiConfiguration;
import fr.becpg.api.helper.CompressParamHelper;
import fr.becpg.api.model.RemoteAPIError;
import fr.becpg.api.model.RemoteAPIException;
import fr.becpg.api.security.WebClientAuthenticationProvider;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

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

	private static Log logger = LogFactory.getLog(AbstractAPIClient.class);
	
	@Autowired
	protected BecpgRestApiConfiguration apiConfiguration;

	@Autowired(required = false)
	protected WebClientAuthenticationProvider authenticationProvider;

	@Bean("remoteWebClient")
	public WebClient webClient() {
		
		String baseUrl = apiConfiguration.getContentServiceUrl() + "/alfresco/service/becpg/remote";
		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
		factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT);

		ConnectionProvider provider = ConnectionProvider.builder("becpg-java-rest-api").maxConnections(50).maxIdleTime(Duration.ofSeconds(20))
				.maxLifeTime(Duration.ofSeconds(60)).pendingAcquireTimeout(Duration.ofSeconds(60)).evictInBackground(Duration.ofSeconds(120)).build();

		HttpClient httpClient = HttpClient.create(provider).wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG,
				AdvancedByteBufFormat.TEXTUAL);

		if (apiConfiguration.shouldDisableSSLVerification()) {
			SslContext sslContext;
			try {
				sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
				httpClient.secure(t -> t.sslContext(sslContext));
			} catch (SSLException e) {
				logger.error("Cannot disable SSL for connection",e);
			}
		
		}

		WebClient.Builder builder = WebClient.builder().codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
				.clientConnector(new ReactorClientHttpConnector(httpClient)).defaultHeaders(header -> {
					if (apiConfiguration.getCustomHeaders() != null) {
						for (Map.Entry<String, String> customHeader : apiConfiguration.getCustomHeaders().entrySet()) {
							header.set(customHeader.getKey(), customHeader.getValue());
						}

					}
				});

		if (authenticationProvider != null) {
			builder = builder.filter(authenticationProvider.authenticationFilter());
		}

		return builder.baseUrl(baseUrl).build();
		
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
