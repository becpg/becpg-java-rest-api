package fr.becpg.api.security;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

class BasicAuthConfigurationTest {

    private MockWebServer mockBackEnd;

    @BeforeEach
    void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    void shouldUseBasicAuthOutsideSessionScope() throws InterruptedException {
        BasicAuthConfiguration configuration = createConfiguration();
        WebClientAuthenticationProvider provider = configuration.authenticationFilter();
        WebClient webClient = createWebClient(provider);

        mockBackEnd.enqueue(new MockResponse().setBody("ok").setResponseCode(HttpStatus.OK.value()));

        String responseBody = webClient.get().uri("/entity").retrieve().bodyToMono(String.class).block();

        Assertions.assertEquals("ok", responseBody);

        RecordedRequest request = mockBackEnd.takeRequest(1, TimeUnit.SECONDS);
        Assertions.assertNotNull(request);
        Assertions.assertEquals("Basic dXNlcjpwd2Q=", request.getHeader("Authorization"));
        Assertions.assertNull(request.getRequestUrl().queryParameter("alf_ticket"));
    }

    @Test
    void shouldReuseAlfTicketWithinSessionScope() throws InterruptedException {
        BasicAuthConfiguration configuration = createConfiguration();
        WebClientAuthenticationProvider provider = configuration.authenticationFilter();
        WebClient webClient = createWebClient(provider);

        mockBackEnd.enqueue(new MockResponse().setBody("<ticket>TICKET-1</ticket>").setResponseCode(HttpStatus.OK.value()));
        mockBackEnd.enqueue(new MockResponse().setBody("first").setResponseCode(HttpStatus.OK.value()));
        mockBackEnd.enqueue(new MockResponse().setBody("second").setResponseCode(HttpStatus.OK.value()));

        provider.doInSession(() -> {
            webClient.get().uri("/entity").retrieve().bodyToMono(String.class).block();
            webClient.get().uri("/channel/list").retrieve().bodyToMono(String.class).block();
            return null;
        });

        RecordedRequest loginRequest = mockBackEnd.takeRequest(1, TimeUnit.SECONDS);
        RecordedRequest firstApiRequest = mockBackEnd.takeRequest(1, TimeUnit.SECONDS);
        RecordedRequest secondApiRequest = mockBackEnd.takeRequest(1, TimeUnit.SECONDS);

        Assertions.assertNotNull(loginRequest);
        Assertions.assertNotNull(firstApiRequest);
        Assertions.assertNotNull(secondApiRequest);

        Assertions.assertTrue(loginRequest.getPath().startsWith("/alfresco/service/api/login?"));
        Assertions.assertEquals("TICKET-1", firstApiRequest.getRequestUrl().queryParameter("alf_ticket"));
        Assertions.assertEquals("TICKET-1", secondApiRequest.getRequestUrl().queryParameter("alf_ticket"));
        Assertions.assertNull(firstApiRequest.getHeader("Authorization"));
        Assertions.assertNull(secondApiRequest.getHeader("Authorization"));
    }

    @Test
    void shouldRefreshTicketAndRetryOnUnauthorized() throws InterruptedException {
        BasicAuthConfiguration configuration = createConfiguration();
        WebClientAuthenticationProvider provider = configuration.authenticationFilter();
        WebClient webClient = createWebClient(provider);

        mockBackEnd.enqueue(new MockResponse().setBody("<ticket>OLD-TICKET</ticket>").setResponseCode(HttpStatus.OK.value()));
        mockBackEnd.enqueue(new MockResponse().setBody("unauthorized").setResponseCode(HttpStatus.UNAUTHORIZED.value()));
        mockBackEnd.enqueue(new MockResponse().setBody("<ticket>NEW-TICKET</ticket>").setResponseCode(HttpStatus.OK.value()));
        mockBackEnd.enqueue(new MockResponse().setBody("ok").setResponseCode(HttpStatus.OK.value()));

        String responseBody = provider.doInSession(() -> webClient.get().uri("/entity").retrieve().bodyToMono(String.class).block());

        Assertions.assertEquals("ok", responseBody);

        RecordedRequest firstLoginRequest = mockBackEnd.takeRequest(1, TimeUnit.SECONDS);
        RecordedRequest firstApiRequest = mockBackEnd.takeRequest(1, TimeUnit.SECONDS);
        RecordedRequest secondLoginRequest = mockBackEnd.takeRequest(1, TimeUnit.SECONDS);
        RecordedRequest secondApiRequest = mockBackEnd.takeRequest(1, TimeUnit.SECONDS);

        Assertions.assertNotNull(firstLoginRequest);
        Assertions.assertNotNull(firstApiRequest);
        Assertions.assertNotNull(secondLoginRequest);
        Assertions.assertNotNull(secondApiRequest);

        Assertions.assertEquals("OLD-TICKET", firstApiRequest.getRequestUrl().queryParameter("alf_ticket"));
        Assertions.assertEquals("NEW-TICKET", secondApiRequest.getRequestUrl().queryParameter("alf_ticket"));
    }

    @Test
    void shouldReuseTicketAcrossDifferentExecutionThreadWithinSessionScope() throws InterruptedException {
        BasicAuthConfiguration configuration = createConfiguration();
        WebClientAuthenticationProvider provider = configuration.authenticationFilter();
        WebClient webClient = createWebClient(provider);

        mockBackEnd.enqueue(new MockResponse().setBody("<ticket>TICKET-THREAD</ticket>").setResponseCode(HttpStatus.OK.value()));
        mockBackEnd.enqueue(new MockResponse().setBody("ok").setResponseCode(HttpStatus.OK.value()));

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            provider.doInSession(() -> {
                Future<String> responseFuture = executorService
                        .submit(() -> webClient.get().uri("/entity").retrieve().bodyToMono(String.class).block());
                try {
                    return responseFuture.get();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IllegalStateException("Interrupted while waiting async API response", e);
                } catch (ExecutionException e) {
                    throw new IllegalStateException("Cannot execute async API response retrieval", e);
                }
            });
        } finally {
            executorService.shutdownNow();
        }

        RecordedRequest loginRequest = mockBackEnd.takeRequest(1, TimeUnit.SECONDS);
        RecordedRequest apiRequest = mockBackEnd.takeRequest(1, TimeUnit.SECONDS);

        Assertions.assertNotNull(loginRequest);
        Assertions.assertNotNull(apiRequest);
        Assertions.assertTrue(loginRequest.getPath().startsWith("/alfresco/service/api/login?"));
        Assertions.assertEquals("TICKET-THREAD", apiRequest.getRequestUrl().queryParameter("alf_ticket"));
        Assertions.assertNull(apiRequest.getHeader("Authorization"));
    }

    private BasicAuthConfiguration createConfiguration() {
        BasicAuthConfiguration configuration = new BasicAuthConfiguration();
        ReflectionTestUtils.setField(configuration, "basicAuthUsername", "user");
        ReflectionTestUtils.setField(configuration, "basicAuthPassword", "pwd");
        ReflectionTestUtils.setField(configuration, "contentServiceUrl", "http://localhost:" + mockBackEnd.getPort());
        return configuration;
    }

    private WebClient createWebClient(WebClientAuthenticationProvider provider) {
        return WebClient.builder().baseUrl("http://localhost:" + mockBackEnd.getPort()).filter(provider.authenticationFilter()).build();
    }
}
