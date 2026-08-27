package fr.becpg.api.security;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;

import fr.becpg.api.RemoteHttpClientFactory;

/**
 * The SDK authenticates through a reactive WebClient, so the OAuth2 client_credentials flow must work whatever the web application type of the
 * integrator: Spring Boot only auto-configures the reactive client registration repository for non-servlet applications.
 */
class OAuth2ConfigurationTest {

    private static final String[] CLIENT_PROPERTIES = {
            "spring.security.oauth2.client.registration.becpg-java-rest-api.provider=becpg-ids",
            "spring.security.oauth2.client.registration.becpg-java-rest-api.client-id=olap-connector@becpg.fr",
            "spring.security.oauth2.client.registration.becpg-java-rest-api.client-secret=secret",
            "spring.security.oauth2.client.registration.becpg-java-rest-api.authorization-grant-type=client_credentials",
            "spring.security.oauth2.client.provider.becpg-ids.token-uri=http://becpg-auth:8080/auth/realms/inst1/protocol/openid-connect/token" };

    @Test
    void shouldProvideReactiveRepositoryInServletApplication() {
        new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(ReactiveOAuth2ClientAutoConfiguration.class))
                .withUserConfiguration(OAuth2Configuration.class, RemoteHttpClientFactory.class)
                .withPropertyValues(CLIENT_PROPERTIES)
                .run(context -> {
                    Assertions.assertThat(context).hasNotFailed();
                    Assertions.assertThat(context).hasSingleBean(ReactiveClientRegistrationRepository.class);
                    Assertions.assertThat(context).hasBean("authenticationFilter");
                });
    }

    @Test
    void shouldRelyOnAutoConfiguredReactiveRepositoryInNonWebApplication() {
        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(ReactiveOAuth2ClientAutoConfiguration.class))
                .withUserConfiguration(OAuth2Configuration.class, RemoteHttpClientFactory.class)
                .withPropertyValues(CLIENT_PROPERTIES)
                .run(context -> {
                    Assertions.assertThat(context).hasNotFailed();
                    Assertions.assertThat(context).hasSingleBean(ReactiveClientRegistrationRepository.class);
                    Assertions.assertThat(context).hasBean("reactiveClientRegistrationRepository");
                    Assertions.assertThat(context).hasBean("authenticationFilter");
                });
    }

    @Test
    void shouldBackOffWhenNoRegistrationConfigured() {
        new WebApplicationContextRunner()
                .withUserConfiguration(OAuth2Configuration.class, RemoteHttpClientFactory.class)
                .run(context -> {
                    Assertions.assertThat(context).hasNotFailed();
                    Assertions.assertThat(context).doesNotHaveBean(ReactiveClientRegistrationRepository.class);
                    Assertions.assertThat(context).doesNotHaveBean("authenticationFilter");
                });
    }
}
