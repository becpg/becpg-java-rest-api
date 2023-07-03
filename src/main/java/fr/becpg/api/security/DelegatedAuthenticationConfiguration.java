package fr.becpg.api.security;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import reactor.core.publisher.Mono;

/**
 * Configuration for external delegated authentication mechanism. This must be provided by the integrator of the SDK in the form of a bean that implements the
 * interface {@link DelegatedAuthenticationProvider}. This authentication method is enabled via the property
 * <code>content.service.security.delegated</code>.
 */
@Configuration
@ConditionalOnProperty("content.service.security.delegated")
public class DelegatedAuthenticationConfiguration {

	@Bean("authenticationFilter")
	WebClientAuthenticationProvider authenticationFilter(DelegatedAuthenticationProvider    delegatedAuthenticationProvider) {
       return () -> ExchangeFilterFunction.ofRequestProcessor(clientRequest -> Mono.just(delegatedAuthenticationProvider.setAuthentication(clientRequest)));
    }
}
