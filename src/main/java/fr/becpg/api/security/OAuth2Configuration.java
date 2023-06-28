package fr.becpg.api.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

@Configuration
@EnableConfigurationProperties({OAuth2ClientProperties.class})
@ConditionalOnProperty("spring.security.oauth2.client.registration.becpg-java-rest-api.provider")
public class OAuth2Configuration {

    private static final String OAUTH2_CLIENT_REGISTRATION_ID = "becpg-java-rest-api";

    @Value("${spring.security.oauth2.client.registration.becpg-java-rest-api.username:#{null}}")
    private String oAuth2Username;
    @Value("${spring.security.oauth2.client.registration.becpg-java-rest-api.password:#{null}}")
    private String oAuth2Password;

    
    @Bean
    ReactiveClientRegistrationRepository getRegistration(
            @Value("${spring.security.oauth2.client.provider.becpg-java-rest-api.token-uri}") String token_uri,
            @Value("${spring.security.oauth2.client.registration.becpg-java-rest-api.client-id}") String client_id,
            @Value("${spring.security.oauth2.client.registration.becpg-java-rest-api.client-secret}") String client_secret
    ) {
        ClientRegistration registration = ClientRegistration
                .withRegistrationId(OAUTH2_CLIENT_REGISTRATION_ID)
                .tokenUri(token_uri)
                .clientId(client_id)
                .clientSecret(client_secret)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .build();
        return new InMemoryReactiveClientRegistrationRepository(registration);
    }

    @Bean("authenticationFilter")
    ExchangeFilterFunction authenticationFilter(ReactiveClientRegistrationRepository clientRegistrations) {
        InMemoryReactiveOAuth2AuthorizedClientService clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations);
        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations, clientService);
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth.setDefaultClientRegistrationId(OAUTH2_CLIENT_REGISTRATION_ID);
        return oauth;

    }
    
}
