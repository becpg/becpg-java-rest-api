package fr.becpg.api.security;

import java.util.function.Supplier;

import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

/**
 * <p>WebClientAuthenticationProvider interface.</p>
 *
 * @author matthieu
 */
public interface WebClientAuthenticationProvider {

	/**
	 * <p>authenticationFilter.</p>
	 *
	 * @return a {@link org.springframework.web.reactive.function.client.ExchangeFilterFunction} object
	 */
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
