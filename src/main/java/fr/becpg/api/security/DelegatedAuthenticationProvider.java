package fr.becpg.api.security;

import org.springframework.web.reactive.function.client.ClientRequest;

/**
 * Interface to be implemented to allow a delegated authentication in the SDK.
 */
@FunctionalInterface
public interface DelegatedAuthenticationProvider {

    /**
     * Set the authentication details in the request template.
     *
     * @param template {@link RequestTemplate} to set the authentication details
     */
    ClientRequest setAuthentication(ClientRequest request);
}
