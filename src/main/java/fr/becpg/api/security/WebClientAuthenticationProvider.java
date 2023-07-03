package fr.becpg.api.security;

import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

public interface WebClientAuthenticationProvider {

	ExchangeFilterFunction authenticationFilter();
	
}
