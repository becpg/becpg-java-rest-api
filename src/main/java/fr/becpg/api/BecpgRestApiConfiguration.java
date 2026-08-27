package fr.becpg.api;

import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import fr.becpg.api.security.WebClientAuthenticationProvider;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

/**
 * <p>BecpgRestApiConfiguration class.</p>
 *
 * @author matthieu
 * @version $Id: $Id
 */
@Configuration
public class BecpgRestApiConfiguration {

	private static Log logger = LogFactory.getLog(BecpgRestApiConfiguration.class);

	@Value("${content.service.url:}")
	private String contentServiceUrl;

	@Value("#{${content.service.headers:{}}}")
	private Map<String, String> customHeaders;

	@Value("${remote.compress.param:false}")
	private Boolean compressParam;

	@Autowired
	private RemoteHttpClientFactory httpClientFactory;

	/**
	 * <p>Getter for the field <code>contentServiceUrl</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getContentServiceUrl() {
		return contentServiceUrl;
	}

	/**
	 * <p>Getter for the field <code>customHeaders</code>.</p>
	 *
	 * @return a {@link java.util.Map} object
	 */
	public Map<String, String> getCustomHeaders() {
		return customHeaders;
	}

	/**
	 * <p>shouldCompressParam.</p>
	 *
	 * @return a boolean
	 */
	public boolean shouldCompressParam() {
		return Boolean.TRUE.equals(compressParam);
	}

	/**
	 * <p>shouldDisableSSLVerification.</p>
	 *
	 * @return a boolean
	 */
	public boolean shouldDisableSSLVerification() {
		return httpClientFactory.shouldDisableSSLVerification();
	}

	/**
	 * <p>Getter for the field <code>forceHttp1</code>.</p>
	 *
	 * @return a {@link java.lang.Boolean} object
	 */
	public boolean shouldForceHttp1() {
		return httpClientFactory.shouldForceHttp1();
	}

	/**
	 * <p>Getter for the field <code>forceTls12</code>.</p>
	 *
	 * @return a {@link java.lang.Boolean} object
	 */
	public boolean shouldForceTls12() {
		return httpClientFactory.shouldForceTls12();
	}

	@Autowired(required = false)
	protected WebClientAuthenticationProvider authenticationProvider;
	

	/**
	 * Execute an operation in the authentication provider session scope.
	 *
	 * @param operation operation to execute
	 * @param <T> operation return type
	 * @return operation result
	 */
	public <T> T doInSession(Supplier<T> operation) {
		if (authenticationProvider != null) {
			return authenticationProvider.doInSession(operation);
		}
		return operation.get();
	}

	/**
	 * <p>webClient.</p>
	 *
	 * @param connectionProvider a {@link reactor.netty.resources.ConnectionProvider} object
	 * @return a {@link org.springframework.web.reactive.function.client.WebClient} object
	 */
	@Bean("remoteWebClient")
	public WebClient webClient(@Autowired(required = false) ConnectionProvider connectionProvider) {

		String baseUrl = getContentServiceUrl() + "/alfresco/service/becpg/remote";

		HttpClient httpClient = httpClientFactory.createReactorHttpClient(connectionProvider, "remote WebClient");

		ReactorClientHttpConnector clientConnector = new ReactorClientHttpConnector(httpClient);

		WebClient.Builder builder = WebClient.builder().codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
				.clientConnector(clientConnector).defaultHeaders(header -> {
					if (getCustomHeaders() != null) {
						for (Map.Entry<String, String> customHeader : getCustomHeaders().entrySet()) {
							header.set(customHeader.getKey(), customHeader.getValue());
						}

					}
				});

		if (authenticationProvider != null) {
			builder = builder.filter(authenticationProvider.authenticationFilter());
		}

		return builder.baseUrl(baseUrl)
				.filters(exchangeFilterFunctions -> {
				      exchangeFilterFunctions.add(logRequest());
				})
				.build();
	}

	 private static ExchangeFilterFunction logRequest() {
	        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
	        	logger.debug("Request: " + clientRequest.method() + " " +  clientRequest.url());
	            clientRequest.headers().forEach((name, values) -> values.forEach(value -> logger.debug(name + ": " + value)));
	            return Mono.just(clientRequest);
	        });
	    }

}
