package fr.becpg.api.security;

import java.util.function.Supplier;

import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

public interface WebClientAuthenticationProvider {

	ExchangeFilterFunction authenticationFilter();

	/**
	 * Execute an operation in the authentication provider session scope.
	 *
	 * @param operation operation to execute
	 * @param <T> operation return type
	 * @return operation result
	 */
	default <T> T doInSession(Supplier<T> operation) {
		return operation.get();
	}
	
}
