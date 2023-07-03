package fr.becpg.api.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;

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


    @Bean("authenticationFilter")
    @ConditionalOnProperty("content.service.security.basicAuth.username")
    WebClientAuthenticationProvider authenticationFilter (){
        return () -> ExchangeFilterFunctions.basicAuthentication(this.basicAuthUsername, this.basicAuthPassword);
    }
}
