package fr.becpg.api.security;

import java.io.IOException;

import javax.net.ssl.SSLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.tls.HandshakeCertificates;
import okhttp3.tls.HeldCertificate;

/**
 * Checks that the Alfresco login call, which does not go through the remote {@code WebClient}, honours {@code remote.ssl.trustAll}.
 */
class BasicAuthConfigurationSslTest {

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
    void shouldRetrieveTicketOverSelfSignedCertificateWhenTrustAllIsEnabled() {
        BasicAuthConfiguration configuration = createConfiguration(true);

        mockBackEnd.enqueue(new MockResponse().setBody("<ticket>TRUST-ALL-TICKET</ticket>").setResponseCode(HttpStatus.OK.value()));

        String ticket = ReflectionTestUtils.invokeMethod(configuration, "retrieveAlfTicket");

        Assertions.assertEquals("TRUST-ALL-TICKET", ticket);
    }

    @Test
    void shouldFailOnSelfSignedCertificateWhenTrustAllIsDisabled() {
        BasicAuthConfiguration configuration = createConfiguration(false);

        mockBackEnd.enqueue(new MockResponse().setBody("<ticket>NEVER-REACHED</ticket>").setResponseCode(HttpStatus.OK.value()));

        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class,
                () -> ReflectionTestUtils.invokeMethod(configuration, "retrieveAlfTicket"));

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

    private BasicAuthConfiguration createConfiguration(boolean sslTrustAll) {
        BasicAuthConfiguration configuration = new BasicAuthConfiguration();
        ReflectionTestUtils.setField(configuration, "basicAuthUsername", "user");
        ReflectionTestUtils.setField(configuration, "basicAuthPassword", "pwd");
        ReflectionTestUtils.setField(configuration, "contentServiceUrl", "https://localhost:" + mockBackEnd.getPort());
        ReflectionTestUtils.setField(configuration, "ticketTtl", 1800000L);
        ReflectionTestUtils.setField(configuration, "sslTrustAll", sslTrustAll);
        ReflectionTestUtils.setField(configuration, "forceHttp1", Boolean.FALSE);
        ReflectionTestUtils.setField(configuration, "forceTls12", Boolean.FALSE);
        ReflectionTestUtils.setField(configuration, "connectTimeoutMs", 30000);
        ReflectionTestUtils.setField(configuration, "responseTimeoutSeconds", 30);
        return configuration;
    }
}
