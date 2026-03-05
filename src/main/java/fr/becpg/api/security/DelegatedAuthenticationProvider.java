package fr.becpg.api.security;

import java.util.function.Supplier;

import org.springframework.web.reactive.function.client.ClientRequest;

/**
 * Interface to be implemented to allow a delegated authentication in the SDK.
 *
 * @author matthieu
 */
@FunctionalInterface
public interface DelegatedAuthenticationProvider {

    /**
     * Set the authentication details in the request template.
     *
     * @param request a {@link org.springframework.web.reactive.function.client.ClientRequest} object
     * @return a {@link org.springframework.web.reactive.function.client.ClientRequest} object
     */
    ClientRequest setAuthentication(ClientRequest request);

	/**
	 * Execute an operation in the delegated authentication session scope.
	 *
	 * @param operation operation to execute
	 * @param <T> operation return type
	 * @return operation result
	 */
	default <T> T doInSession(Supplier<T> operation) {
		return operation.get();
	}
}
