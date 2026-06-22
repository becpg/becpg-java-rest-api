package fr.becpg.api.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import reactor.core.publisher.Mono;

/**
 * Configuration for basic authentication. It configures a {@link BasicAuthRequestInterceptor} that uses the credentials configured in the properties
 * <code>content.service.security.basicAuth.username</code> and <code>content.service.security.basicAuth.password</code>.
 *
 * @author matthieu
 */
@Configuration
@ConditionalOnExpression("'${content.service.security.basicAuth.username:}' != '' and '${spring.security.oauth2.client.registration.becpg-java-rest-api.provider:}' == ''")
public class BasicAuthConfiguration {

    private static final Log logger = LogFactory.getLog(BasicAuthConfiguration.class);

    private static final String ALF_TICKET_PARAMETER = "alf_ticket";
    private static final String ALF_LOGIN_ENDPOINT = "/alfresco/service/api/login";

    /**
     * Shared HTTP client used to retrieve Alfresco tickets, avoiding the creation of a new client on every login call.
     */
    private static final HttpClient LOGIN_HTTP_CLIENT = HttpClient.newHttpClient();

    @Value("${content.service.security.basicAuth.username:#{null}}")
    private String basicAuthUsername;
    @Value("${content.service.security.basicAuth.password:#{null}}")
    private String basicAuthPassword;
    @Value("${content.service.url:}")
    private String contentServiceUrl;
    /**
     * Time-to-live (in milliseconds) of the alf_ticket cached outside of a {@code doInSession} scope. Defaults to 30 minutes. This avoids sending the
     * Basic credentials (and thus a Keycloak password validation) on every single request made outside of a connector job.
     */
    @Value("${content.service.security.basicAuth.ticketTtl:1800000}")
    private long ticketTtl;

    private final AtomicInteger activeSessionCount = new AtomicInteger(0);
    private final AtomicReference<String> cachedSessionAlfTicket = new AtomicReference<>();
    private final Object sessionTicketLock = new Object();

    private final AtomicReference<String> cachedNoSessionTicket = new AtomicReference<>();
    private final AtomicLong noSessionTicketExpiry = new AtomicLong(0);
    private final Object noSessionTicketLock = new Object();

    @Bean("remoteAuthenticationFilter")
    WebClientAuthenticationProvider authenticationFilter (){

        return new WebClientAuthenticationProvider() {

            @Override
            public <T> T doInSession(Supplier<T> operation) {
                activeSessionCount.incrementAndGet();
                try {
                    return operation.get();
                } finally {
                    int currentSessionCount = activeSessionCount.decrementAndGet();
                    if (currentSessionCount <= 0) {
                        activeSessionCount.set(0);
                        cachedSessionAlfTicket.set(null);
                    }
                }
            }

			@Override
			public ExchangeFilterFunction authenticationFilter() {
				return (request, next) -> {
					if (activeSessionCount.get() <= 0) {
						return executeWithNoSessionTicket(request, next);
					}
					return executeWithTicket(request, next);
				};
			}

			/**
			 * Execute a request outside of a {@code doInSession} scope using a TTL-cached alf_ticket instead of sending the Basic credentials (and thus
			 * triggering a Keycloak password validation) on every request.
			 */
			private Mono<ClientResponse> executeWithNoSessionTicket(ClientRequest request, ExchangeFunction next) {
				String ticket = getOrCreateNoSessionTicket();
				ClientRequest ticketRequest = ClientRequest.from(request)
						.url(addAlfTicket(request.url(), ticket))
						.build();

				return next.exchange(ticketRequest).flatMap(response -> {
					if (response.statusCode().value() == 401) {
						return retryWithRefreshedNoSessionTicket(request, next);
					}
					return Mono.just(response);
				});
			}

			private Mono<ClientResponse> retryWithRefreshedNoSessionTicket(ClientRequest request, ExchangeFunction next) {
				logger.warn("Received HTTP 401 with current alf_ticket outside session scope, refreshing ticket and retrying request");
				String refreshedTicket = refreshNoSessionTicket();
				ClientRequest retryRequest = ClientRequest.from(request)
						.url(addAlfTicket(request.url(), refreshedTicket))
						.build();
				return next.exchange(retryRequest);
			}

			private Mono<ClientResponse> executeWithTicket(ClientRequest request, ExchangeFunction next) {
				String ticket = getOrCreateSessionAlfTicket();
				ClientRequest ticketRequest = ClientRequest.from(request)
						.url(addAlfTicket(request.url(), ticket))
						.build();

				return next.exchange(ticketRequest).flatMap(response -> {
					if (response.statusCode().value() == 401) {
						return retryWithRefreshedTicket(request, next);
					}
					return Mono.just(response);
				});
			}

			private Mono<ClientResponse> retryWithRefreshedTicket(ClientRequest request, ExchangeFunction next) {
				logger.warn("Received HTTP 401 with current alf_ticket, refreshing ticket and retrying request");
				String refreshedTicket = refreshSessionAlfTicket();
				ClientRequest retryRequest = ClientRequest.from(request)
						.url(addAlfTicket(request.url(), refreshedTicket))
						.build();
				return next.exchange(retryRequest);
			}
		};
    }

	/**
	 * Return the cached session ticket or retrieve it once when absent.
	 *
	 * @return alfresco ticket for current connector execution scope
	 */
	private String getOrCreateSessionAlfTicket() {
		String ticket = cachedSessionAlfTicket.get();
		if (ticket != null && !ticket.isBlank()) {
			return ticket;
		}

		synchronized (sessionTicketLock) {
			String lockedTicket = cachedSessionAlfTicket.get();
			if (lockedTicket != null && !lockedTicket.isBlank()) {
				return lockedTicket;
			}
			String retrievedTicket = retrieveAlfTicket();
			cachedSessionAlfTicket.set(retrievedTicket);
			return retrievedTicket;
		}
	}

	/**
	 * Force a refresh of the cached session ticket.
	 *
	 * @return refreshed alfresco ticket
	 */
	private String refreshSessionAlfTicket() {
		synchronized (sessionTicketLock) {
			String refreshedTicket = retrieveAlfTicket();
			cachedSessionAlfTicket.set(refreshedTicket);
			return refreshedTicket;
		}
	}

	/**
	 * Return the alf_ticket cached for out-of-session requests or retrieve a fresh one when missing or expired.
	 *
	 * @return alfresco ticket reused across out-of-session requests within the configured TTL
	 */
	private String getOrCreateNoSessionTicket() {
		String ticket = cachedNoSessionTicket.get();
		if (ticket != null && !ticket.isBlank() && System.currentTimeMillis() < noSessionTicketExpiry.get()) {
			return ticket;
		}

		synchronized (noSessionTicketLock) {
			String lockedTicket = cachedNoSessionTicket.get();
			if (lockedTicket != null && !lockedTicket.isBlank() && System.currentTimeMillis() < noSessionTicketExpiry.get()) {
				return lockedTicket;
			}
			return retrieveAndCacheNoSessionTicket();
		}
	}

	/**
	 * Force a refresh of the out-of-session cached ticket, typically after a 401 response.
	 *
	 * @return refreshed alfresco ticket
	 */
	private String refreshNoSessionTicket() {
		synchronized (noSessionTicketLock) {
			return retrieveAndCacheNoSessionTicket();
		}
	}

	private String retrieveAndCacheNoSessionTicket() {
		String retrievedTicket = retrieveAlfTicket();
		cachedNoSessionTicket.set(retrievedTicket);
		noSessionTicketExpiry.set(System.currentTimeMillis() + ticketTtl);
		return retrievedTicket;
	}

	/**
	 * Retrieve an Alfresco ticket using the configured Basic credentials.
	 *
	 * @return the retrieved alf_ticket value
	 */
	private String retrieveAlfTicket() {
		String loginUrl = buildLoginUrl();
		HttpRequest request = HttpRequest.newBuilder(URI.create(loginUrl)).GET().build();

		try {
			HttpResponse<byte[]> response = LOGIN_HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofByteArray());
			if (response.statusCode() >= 400) {
				throw new IllegalStateException("Cannot retrieve alf_ticket. Status: " + response.statusCode());
			}
			String ticket = extractTicket(response.body());
			if (ticket == null || ticket.isBlank()) {
				throw new IllegalStateException("Cannot retrieve alf_ticket from login response");
			}
			return ticket;
		} catch (IOException e) {
			throw new IllegalStateException("Cannot retrieve alf_ticket due to IO error", e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException("Cannot retrieve alf_ticket because operation was interrupted", e);
		}
	}

	/**
	 * Build Alfresco login URL with encoded username and password.
	 *
	 * @return login URL
	 */
	private String buildLoginUrl() {
		String separator = contentServiceUrl.endsWith("/") ? "" : "/";
		String encodedUser = URLEncoder.encode(basicAuthUsername, StandardCharsets.UTF_8);
		String encodedPassword = URLEncoder.encode(basicAuthPassword, StandardCharsets.UTF_8);
		return contentServiceUrl + separator + ALF_LOGIN_ENDPOINT.substring(1) + "?u=" + encodedUser + "&pw=" + encodedPassword;
	}

	/**
	 * Extract the <code>ticket</code> XML element from Alfresco login response.
	 *
	 * @param responseBody login response body
	 * @return ticket value or {@code null} if not found
	 */
	private String extractTicket(byte[] responseBody) {
		try {
			DocumentBuilderFactory domFactory = createSecureDocumentBuilderFactory();
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document document = builder.parse(new ByteArrayInputStream(responseBody));
			NodeList nodes = document.getElementsByTagName("ticket");
			if (nodes != null && nodes.getLength() > 0) {
				return nodes.item(0).getTextContent();
			}
			return null;
		} catch (Exception e) {
			throw new IllegalStateException("Cannot parse alf_ticket from login response", e);
		}
	}

	/**
	 * Create a secure XML document builder factory.
	 *
	 * @return secured {@link DocumentBuilderFactory}
	 * @throws ParserConfigurationException when parser cannot be configured
	 */
	private DocumentBuilderFactory createSecureDocumentBuilderFactory() throws ParserConfigurationException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		domFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		domFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		domFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
		domFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		domFactory.setExpandEntityReferences(false);
		return domFactory;
	}

	/**
	 * Add alf_ticket query parameter to request URL when absent.
	 *
	 * @param url original request URL
	 * @param ticket alf_ticket value
	 * @return URL containing alf_ticket query parameter
	 */
	private URI addAlfTicket(URI url, String ticket) {
		if (url.getQuery() != null && url.getQuery().contains(ALF_TICKET_PARAMETER + "=")) {
			return url;
		}
		
		return UriComponentsBuilder.fromUri(url).queryParam(ALF_TICKET_PARAMETER, ticket).build(true).toUri();
	}
}
