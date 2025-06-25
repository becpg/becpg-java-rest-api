package fr.becpg.api;

import java.util.Map;

import javax.net.ssl.SSLException;

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
import io.netty.handler.logging.LogLevel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

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

	@Value("${remote.ssl.trustAll:false}")
	private Boolean sslTrustAll;

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

	public boolean shouldCompressParam() {
		return Boolean.TRUE.equals(compressParam);
	}

	public boolean shouldDisableSSLVerification() {
		return Boolean.TRUE.equals(sslTrustAll);
	}

	@Autowired(required = false)
	protected WebClientAuthenticationProvider authenticationProvider;
	
	@Autowired(required = false)
	private ConnectionProvider connectionProvider;

	@Bean("remoteWebClient")
	public WebClient webClient() {

		String baseUrl = getContentServiceUrl() + "/alfresco/service/becpg/remote";

		HttpClient httpClient = connectionProvider != null ? HttpClient.create(connectionProvider) : HttpClient.create();
		
		if(logger.isDebugEnabled()) {
			httpClient.wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
		}
		
		if (shouldDisableSSLVerification()) {
			SslContext sslContext;
			try {
				sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
				httpClient = httpClient.secure(t -> t.sslContext(sslContext));

				logger.debug("SSL verification disabled successfully");
			} catch (SSLException e) {
				logger.error("Cannot disable SSL for connection", e);
			}
		} else {
			logger.debug("SSL verification is enabled");
		}

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
