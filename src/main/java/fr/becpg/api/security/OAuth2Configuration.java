package fr.becpg.api.security;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;

/**
 * <p>OAuth2Configuration class.</p>
 *
 * @author matthieu
 */
@Configuration
@EnableConfigurationProperties({OAuth2ClientProperties.class})
@ConditionalOnProperty("spring.security.oauth2.client.registration.becpg-java-rest-api.provider")
public class OAuth2Configuration {

    private static final String OAUTH2_CLIENT_REGISTRATION_ID = "becpg-java-rest-api";


    @Bean("authenticationFilter")
    WebClientAuthenticationProvider authenticationFilter(ReactiveClientRegistrationRepository clientRegistrations) {
        InMemoryReactiveOAuth2AuthorizedClientService clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations);
        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations, clientService);

        // client_credentials is the recommended technical flow (token cached in memory and only re-fetched on expiry).
        // refreshToken is added so that any flow issuing a refresh_token reuses it instead of re-authenticating fully, further reducing Keycloak load.
        ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider = ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .refreshToken()
                .build();
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth.setDefaultClientRegistrationId(OAUTH2_CLIENT_REGISTRATION_ID);
        return () -> oauth;

    }
    
}
