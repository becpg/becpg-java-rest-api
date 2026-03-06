package fr.becpg.api.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
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

    @Value("${content.service.security.basicAuth.username:#{null}}")
    private String basicAuthUsername;
    @Value("${content.service.security.basicAuth.password:#{null}}")
    private String basicAuthPassword;
    @Value("${content.service.url:}")
    private String contentServiceUrl;

    private final AtomicInteger activeSessionCount = new AtomicInteger(0);
    private final AtomicReference<String> cachedSessionAlfTicket = new AtomicReference<>();
    private final Object sessionTicketLock = new Object();

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
						return executeWithBasicAuth(request, next);
					}
					return executeWithTicket(request, next);
				};
			}

			private Mono<ClientResponse> executeWithBasicAuth(ClientRequest request, ExchangeFunction next) {
				if (logger.isDebugEnabled()) {
					logger.debug("Executing request with direct Basic authentication outside doInSession scope");
				}
				ClientRequest basicRequest = ClientRequest.from(request)
						.headers(headers -> headers.setBasicAuth(basicAuthUsername, basicAuthPassword))
						.build();
				return next.exchange(basicRequest);
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
	 * Retrieve an Alfresco ticket using the configured Basic credentials.
	 *
	 * @return the retrieved alf_ticket value
	 */
	private String retrieveAlfTicket() {
		String loginUrl = buildLoginUrl();
		HttpRequest request = HttpRequest.newBuilder(URI.create(loginUrl)).GET().build();

		try {
			HttpResponse<byte[]> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofByteArray());
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
		try {
			String query = url.getQuery();
			if (query != null && query.contains(ALF_TICKET_PARAMETER + "=")) {
				return url;
			}

			String separator = (query == null || query.isBlank()) ? "" : "&";
			String encodedTicket = URLEncoder.encode(ticket, StandardCharsets.UTF_8);
			String updatedQuery = (query == null ? "" : query) + separator + ALF_TICKET_PARAMETER + "=" + encodedTicket;
			return new URI(url.getScheme(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), updatedQuery, url.getFragment());
		} catch (URISyntaxException e) {
			throw new IllegalStateException("Cannot append alf_ticket to request URL", e);
		}
	}
}
