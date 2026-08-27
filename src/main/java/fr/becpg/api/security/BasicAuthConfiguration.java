package fr.becpg.api.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
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

    private static final String TLS12_PROTOCOL = "TLSv1.2";

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

    /**
     * The login call does not go through the {@code remoteWebClient}, so it has to honour the same {@code remote.*} client settings on its own,
     * otherwise a repository published behind a self-signed or company-signed certificate fails the login handshake even with
     * {@code remote.ssl.trustAll=true}.
     */
    @Value("${remote.ssl.trustAll:false}")
    private Boolean sslTrustAll;
    @Value("${remote.force.http1:false}")
    private Boolean forceHttp1;
    @Value("${remote.force.tls12:false}")
    private Boolean forceTls12;
    @Value("${remote.connect.timeout:30000}")
    private Integer connectTimeoutMs;
    @Value("${remote.response.timeout:300}")
    private Integer responseTimeoutSeconds;

    /**
     * HTTP client used to retrieve Alfresco tickets, built once on first login so that it picks up the injected {@code remote.*} properties, and then
     * shared to avoid creating a client on every login call.
     */
    private final AtomicReference<HttpClient> loginHttpClient = new AtomicReference<>();
    private final Object loginHttpClientLock = new Object();

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
	 * Return the shared login client, building it on first use from the {@code remote.*} properties.
	 *
	 * @return HTTP client used for the Alfresco login call
	 */
	private HttpClient getLoginHttpClient() {
		HttpClient client = loginHttpClient.get();
		if (client != null) {
			return client;
		}

		synchronized (loginHttpClientLock) {
			HttpClient lockedClient = loginHttpClient.get();
			if (lockedClient != null) {
				return lockedClient;
			}
			HttpClient builtClient = buildLoginHttpClient();
			loginHttpClient.set(builtClient);
			return builtClient;
		}
	}

	/**
	 * Build the login client with the same SSL, protocol and timeout settings as the remote {@link org.springframework.web.reactive.function.client.WebClient}.
	 *
	 * @return configured HTTP client
	 */
	private HttpClient buildLoginHttpClient() {
		HttpClient.Builder builder = HttpClient.newBuilder();

		if (connectTimeoutMs != null && connectTimeoutMs > 0) {
			builder = builder.connectTimeout(Duration.ofMillis(connectTimeoutMs));
		}

		if (Boolean.TRUE.equals(forceHttp1)) {
			builder = builder.version(HttpClient.Version.HTTP_1_1);
			logger.info("HTTP/1.1 forced for Alfresco login client");
		}

		if (Boolean.TRUE.equals(forceTls12)) {
			SSLParameters sslParameters = new SSLParameters();
			sslParameters.setProtocols(new String[] { TLS12_PROTOCOL });
			builder = builder.sslParameters(sslParameters);
			logger.info("TLSv1.2 forced for Alfresco login client");
		}

		if (Boolean.TRUE.equals(sslTrustAll)) {
			builder = builder.sslContext(createTrustAllSslContext());
			logger.warn("SSL verification disabled for Alfresco login client");
		}

		return builder.build();
	}

	/**
	 * Build an SSL context trusting every certificate, so that {@code remote.ssl.trustAll=true} also applies to the login call. The trust manager is an
	 * {@link javax.net.ssl.X509ExtendedTrustManager} on purpose: the JDK HTTP client always sets the {@code HTTPS} endpoint identification algorithm,
	 * and only an extended trust manager skips both the certificate path validation and the hostname check.
	 *
	 * @return SSL context accepting any server certificate
	 */
	private SSLContext createTrustAllSslContext() {
		try {
			SSLContext sslContext = SSLContext.getInstance(Boolean.TRUE.equals(forceTls12) ? TLS12_PROTOCOL : "TLS");
			sslContext.init(null, new TrustManager[] { trustAllManager() }, new SecureRandom());
			return sslContext;
		} catch (GeneralSecurityException e) {
			throw new IllegalStateException("Cannot disable SSL verification for Alfresco login client", e);
		}
	}

	private X509ExtendedTrustManager trustAllManager() {
		return new X509ExtendedTrustManager() {

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) {
				// trust all
			}

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) {
				// trust all
			}

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) {
				// trust all
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) {
				// trust all
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) {
				// trust all
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) {
				// trust all
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
		};
	}

	/**
	 * Retrieve an Alfresco ticket using the configured Basic credentials.
	 *
	 * @return the retrieved alf_ticket value
	 */
	private String retrieveAlfTicket() {
		String loginUrl = buildLoginUrl();
		HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(loginUrl)).GET();
		if (responseTimeoutSeconds != null && responseTimeoutSeconds > 0) {
			requestBuilder = requestBuilder.timeout(Duration.ofSeconds(responseTimeoutSeconds));
		}
		HttpRequest request = requestBuilder.build();

		try {
			HttpResponse<byte[]> response = getLoginHttpClient().send(request, HttpResponse.BodyHandlers.ofByteArray());
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
