package fr.becpg.api.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;

import reactor.core.publisher.Mono;

@Configuration
@EnableConfigurationProperties({OAuth2ClientProperties.class})
@ConditionalOnProperty("spring.security.oauth2.client.registration.becpg-java-rest-api.provider")
public class OAuth2Configuration {

    private static final String OAUTH2_CLIENT_REGISTRATION_ID = "becpg-java-rest-api";

	@Value("${content.service.security.preferSessionCookie:true}")
	private Boolean preferSessionCookie;

	private final RemoteSessionCookieStore sessionCookieStore;

	/**
	 * @param sessionCookieStore session cookie store
	 */
	public OAuth2Configuration(RemoteSessionCookieStore sessionCookieStore) {
		super();
		this.sessionCookieStore = sessionCookieStore;
	}


    @Bean("authenticationFilter")
    WebClientAuthenticationProvider authenticationFilter(ReactiveClientRegistrationRepository clientRegistrations) {
        InMemoryReactiveOAuth2AuthorizedClientService clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations);
        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations, clientService);
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth.setDefaultClientRegistrationId(OAUTH2_CLIENT_REGISTRATION_ID);
		return () -> (request, next) -> {
			boolean shouldUseSessionCookie = Boolean.TRUE.equals(preferSessionCookie) && sessionCookieStore.hasCookies(request.url());
			if (!shouldUseSessionCookie) {
				return oauth.filter(request, next);
			}

			return next.exchange(request).flatMap(response -> {
				if (response.statusCode().value() == 401) {
					return oauth.filter(request, next);
				}
				return Mono.just(response);
			});
		};

    }
    
}
