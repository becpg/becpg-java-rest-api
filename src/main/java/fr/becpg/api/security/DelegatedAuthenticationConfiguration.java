package fr.becpg.api.security;

import java.util.function.Supplier;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import reactor.core.publisher.Mono;

/**
 * Configuration for external delegated authentication mechanism. This must be provided by the integrator of the SDK in the form of a bean that implements the
 * interface {@link fr.becpg.api.security.DelegatedAuthenticationProvider}. This authentication method is enabled via the property
 * <code>content.service.security.delegated</code>.
 *
 * @author matthieu
 */
@Configuration
@ConditionalOnProperty("content.service.security.delegated")
public class DelegatedAuthenticationConfiguration {

	@Bean("remoteAuthenticationFilter")
	WebClientAuthenticationProvider authenticationFilter(DelegatedAuthenticationProvider    delegatedAuthenticationProvider) {
		return new WebClientAuthenticationProvider() {
			@Override
			public ExchangeFilterFunction authenticationFilter() {
				return ExchangeFilterFunction
						.ofRequestProcessor(clientRequest -> Mono.just(delegatedAuthenticationProvider.setAuthentication(clientRequest)));
			}

			@Override
			public <T> T doInSession(Supplier<T> operation) {
				return delegatedAuthenticationProvider.doInSession(operation);
			}
		};
    }
}
