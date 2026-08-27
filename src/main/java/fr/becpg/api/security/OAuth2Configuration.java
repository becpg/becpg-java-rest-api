package fr.becpg.api.security;


import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.endpoint.WebClientReactiveClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.WebClientReactiveRefreshTokenTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import fr.becpg.api.RemoteHttpClientFactory;

/**
 * <p>OAuth2Configuration class.</p>
 *
 * @author matthieu
 */
@Configuration
@EnableConfigurationProperties({OAuth2ClientProperties.class})
@ConditionalOnExpression("'${spring.security.oauth2.client.registration.becpg-java-rest-api.provider:}' != ''")
public class OAuth2Configuration {

    private static final String OAUTH2_CLIENT_REGISTRATION_ID = "becpg-java-rest-api";

    /**
     * The beCPG REST API authenticates through a {@link org.springframework.web.reactive.function.client.WebClient}, hence it always needs the
     * <b>reactive</b> client registration repository. Spring Boot only auto-configures that one for non-servlet applications
     * ({@code ReactiveOAuth2ClientAutoConfiguration} backs off as soon as the application is a servlet web application), so an integrator embedding
     * the SDK in a servlet application would otherwise fail to start on a missing
     * {@link org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository} bean.
     *
     * @param properties the {@code spring.security.oauth2.client} properties
     * @return a {@link org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository} object
     */
    @Bean
    @ConditionalOnWebApplication(type = Type.SERVLET)
    @ConditionalOnMissingBean(ReactiveClientRegistrationRepository.class)
    ReactiveClientRegistrationRepository reactiveClientRegistrationRepository(OAuth2ClientProperties properties) {
        List<ClientRegistration> registrations = new ArrayList<>(new OAuth2ClientPropertiesMapper(properties).asClientRegistrations().values());
        return new InMemoryReactiveClientRegistrationRepository(registrations);
    }

    @Bean("authenticationFilter")
    WebClientAuthenticationProvider authenticationFilter(ReactiveClientRegistrationRepository clientRegistrations,
            RemoteHttpClientFactory httpClientFactory) {
        InMemoryReactiveOAuth2AuthorizedClientService clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations);
        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations, clientService);

        // The token endpoint is called by its own WebClient, which Spring Security builds with a default connector: without this it would ignore the
        // remote.* settings, and an identity provider published behind a self-signed or company-signed certificate would fail the handshake even with
        // remote.ssl.trustAll=true.
        WebClient tokenWebClient = createTokenWebClient(httpClientFactory);

        WebClientReactiveClientCredentialsTokenResponseClient clientCredentialsTokenClient = new WebClientReactiveClientCredentialsTokenResponseClient();
        clientCredentialsTokenClient.setWebClient(tokenWebClient);

        WebClientReactiveRefreshTokenTokenResponseClient refreshTokenTokenClient = new WebClientReactiveRefreshTokenTokenResponseClient();
        refreshTokenTokenClient.setWebClient(tokenWebClient);

        // client_credentials is the recommended technical flow (token cached in memory and only re-fetched on expiry).
        // refreshToken is added so that any flow issuing a refresh_token reuses it instead of re-authenticating fully, further reducing Keycloak load.
        ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider = ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials(clientCredentials -> clientCredentials.accessTokenResponseClient(clientCredentialsTokenClient))
                .refreshToken(refreshToken -> refreshToken.accessTokenResponseClient(refreshTokenTokenClient))
                .build();
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth.setDefaultClientRegistrationId(OAUTH2_CLIENT_REGISTRATION_ID);
        return () -> oauth;

    }

    private WebClient createTokenWebClient(RemoteHttpClientFactory httpClientFactory) {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClientFactory.createReactorHttpClient(null, "OAuth2 token client")))
                .build();
    }

}
