package fr.becpg.api;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.web.reactive.function.client.WebClient;

import fr.becpg.api.handler.ChannelAPIClient;
import fr.becpg.api.handler.ContentAPIClient;
import fr.becpg.api.handler.EntityAPIClient;

/**
 * An integrator only gets the SDK beans through the auto-configuration imports: its own application scans its own package, not {@code fr.becpg.api}.
 */
class BecpgRestApiAutoConfigurationTest {

    @Test
    void shouldProvideRemoteWebClientToAnApplicationThatDoesNotScanTheSdkPackage() {
        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(RemoteHttpClientFactory.class, BecpgRestApiConfiguration.class))
                .withPropertyValues("content.service.url=http://localhost:8080", "content.service.headers={'x-custom':'value'}")
                .run(context -> {
                    Assertions.assertThat(context).hasNotFailed();
                    Assertions.assertThat(context).hasSingleBean(RemoteHttpClientFactory.class);
                    Assertions.assertThat(context).hasSingleBean(BecpgRestApiConfiguration.class);
                    Assertions.assertThat(context).getBean("remoteWebClient").isInstanceOf(WebClient.class);
                });
    }

    @Test
    void shouldImportEveryApiClient() {
        List<String> imports = new ArrayList<>();
        ImportCandidates.load(AutoConfiguration.class, getClass().getClassLoader()).forEach(imports::add);

        Assertions.assertThat(imports).contains(EntityAPIClient.class.getName(), ContentAPIClient.class.getName(), ChannelAPIClient.class.getName());
    }
}
