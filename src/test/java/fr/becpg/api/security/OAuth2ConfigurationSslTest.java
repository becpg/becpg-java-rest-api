package fr.becpg.api.security;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import fr.becpg.api.RemoteHttpClientFactory;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okhttp3.tls.HandshakeCertificates;
import okhttp3.tls.HeldCertificate;

/**
 * Checks that the OAuth2 token call, which Spring Security makes with its own WebClient, honours {@code remote.ssl.trustAll}.
 */
class OAuth2ConfigurationSslTest {

    private static final String TOKEN_RESPONSE = "{\"access_token\":\"TOKEN\",\"token_type\":\"Bearer\",\"expires_in\":3600}";

    private MockWebServer mockBackEnd;

    @BeforeEach
    void setUp() throws IOException {
        // Deliberately issued for another host: trustAll has to skip the hostname check as well, not only the certificate path validation.
        HeldCertificate serverCertificate = new HeldCertificate.Builder().addSubjectAlternativeName("other.invalid").build();
        HandshakeCertificates serverCertificates = new HandshakeCertificates.Builder().heldCertificate(serverCertificate).build();

        mockBackEnd = new MockWebServer();
        mockBackEnd.useHttps(serverCertificates.sslSocketFactory(), false);
        mockBackEnd.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    void shouldRetrieveTokenOverSelfSignedCertificateWhenTrustAllIsEnabled() throws InterruptedException {
        RemoteHttpClientFactory httpClientFactory = createHttpClientFactory(true);
        WebClient webClient = createWebClient(httpClientFactory);

        mockBackEnd.enqueue(new MockResponse().setBody(TOKEN_RESPONSE).setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .setResponseCode(HttpStatus.OK.value()));
        mockBackEnd.enqueue(new MockResponse().setBody("ok").setResponseCode(HttpStatus.OK.value()));

        String responseBody = webClient.get().uri("/entity").retrieve().bodyToMono(String.class).block();

        Assertions.assertEquals("ok", responseBody);

        RecordedRequest tokenRequest = mockBackEnd.takeRequest(1, TimeUnit.SECONDS);
        RecordedRequest apiRequest = mockBackEnd.takeRequest(1, TimeUnit.SECONDS);

        Assertions.assertNotNull(tokenRequest);
        Assertions.assertNotNull(apiRequest);
        Assertions.assertEquals("/token", tokenRequest.getPath());
        Assertions.assertEquals("Bearer TOKEN", apiRequest.getHeader("Authorization"));
    }

    @Test
    void shouldFailOnSelfSignedCertificateWhenTrustAllIsDisabled() {
        RemoteHttpClientFactory httpClientFactory = createHttpClientFactory(false);
        WebClient webClient = createWebClient(httpClientFactory);

        mockBackEnd.enqueue(new MockResponse().setBody(TOKEN_RESPONSE).setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .setResponseCode(HttpStatus.OK.value()));

        Exception exception = Assertions.assertThrows(Exception.class,
                () -> webClient.get().uri("/entity").retrieve().bodyToMono(String.class).block());

        Assertions.assertTrue(hasSslCause(exception), "Expected an SSL failure but got: " + exception);
    }

    private boolean hasSslCause(Throwable throwable) {
        for (Throwable cause = throwable; cause != null; cause = cause.getCause()) {
            if (cause instanceof SSLException) {
                return true;
            }
        }
        return false;
    }

    private WebClient createWebClient(RemoteHttpClientFactory httpClientFactory) {
        String baseUrl = "https://localhost:" + mockBackEnd.getPort();

        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("becpg-java-rest-api").clientId("connector")
                .clientSecret("secret").authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS).tokenUri(baseUrl + "/token").build();
        ReactiveClientRegistrationRepository clientRegistrations = new InMemoryReactiveClientRegistrationRepository(clientRegistration);

        WebClientAuthenticationProvider provider = new OAuth2Configuration().authenticationFilter(clientRegistrations, httpClientFactory);

        return WebClient.builder().baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClientFactory.createReactorHttpClient(null, "test client")))
                .filter(provider.authenticationFilter()).build();
    }

    private RemoteHttpClientFactory createHttpClientFactory(boolean sslTrustAll) {
        RemoteHttpClientFactory httpClientFactory = new RemoteHttpClientFactory();
        ReflectionTestUtils.setField(httpClientFactory, "sslTrustAll", sslTrustAll);
        ReflectionTestUtils.setField(httpClientFactory, "forceHttp1", Boolean.FALSE);
        ReflectionTestUtils.setField(httpClientFactory, "forceTls12", Boolean.FALSE);
        ReflectionTestUtils.setField(httpClientFactory, "connectTimeoutMs", 30000);
        ReflectionTestUtils.setField(httpClientFactory, "responseTimeoutSeconds", 30);
        return httpClientFactory;
    }
}
