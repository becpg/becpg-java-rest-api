package fr.becpg.api.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;

import reactor.core.publisher.Mono;

/**
 * Configuration for basic authentication. It configures a {@link BasicAuthRequestInterceptor} that uses the credentials configured in the properties
 * <code>content.service.security.basicAuth.username</code> and <code>content.service.security.basicAuth.password</code>.
 */
@Configuration
@ConditionalOnProperty("content.service.security.basicAuth.username")
public class BasicAuthConfiguration {

    @Value("${content.service.security.basicAuth.username:#{null}}")
    private String basicAuthUsername;
    @Value("${content.service.security.basicAuth.password:#{null}}")
    private String basicAuthPassword;

	@Value("${content.service.security.preferSessionCookie:true}")
	private Boolean preferSessionCookie;

	private final RemoteSessionCookieStore sessionCookieStore;

	/**
	 * @param sessionCookieStore session cookie store
	 */
	public BasicAuthConfiguration(RemoteSessionCookieStore sessionCookieStore) {
		super();
		this.sessionCookieStore = sessionCookieStore;
	}


    @Bean("remoteAuthenticationFilter")
    @ConditionalOnProperty("content.service.security.basicAuth.username")
    WebClientAuthenticationProvider authenticationFilter (){
		return () -> (request, next) -> {
			boolean shouldUseSessionCookie = Boolean.TRUE.equals(preferSessionCookie) && sessionCookieStore.hasCookies(request.url());
			ClientRequest outboundRequest = shouldUseSessionCookie ? request
					: ClientRequest.from(request).headers(headers -> headers.setBasicAuth(basicAuthUsername, basicAuthPassword)).build();

			return next.exchange(outboundRequest).flatMap(response -> {
				if (shouldUseSessionCookie && response.statusCode().value() == 401) {
					ClientRequest retryRequest = ClientRequest.from(request)
							.headers(headers -> headers.setBasicAuth(basicAuthUsername, basicAuthPassword)).build();
					return next.exchange(retryRequest);
				}
				return Mono.just(response);
			});
		};
    }
}
