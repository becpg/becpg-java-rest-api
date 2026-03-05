package fr.becpg.api.security;

import java.net.URI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

class RemoteSessionCookieStoreTest {

    @Test
    void shouldBindHostOnlyCookieToOriginalHost() {
        RemoteSessionCookieStore cookieStore = new RemoteSessionCookieStore();
        cookieStore.beginSession();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, "JSESSIONID=session-1; Path=/alfresco; HttpOnly");

        URI sourceUri = URI.create("http://repo-a.local/alfresco/service/becpg/remote/entity");
        cookieStore.updateFromResponse(sourceUri, responseHeaders);

        String sourceHeader = cookieStore.buildCookieHeader(URI.create("http://repo-a.local/alfresco/service/becpg/remote/channel/list"));
        String otherHostHeader = cookieStore.buildCookieHeader(URI.create("http://repo-b.local/alfresco/service/becpg/remote/channel/list"));

        Assertions.assertNotNull(sourceHeader);
        Assertions.assertTrue(sourceHeader.contains("JSESSIONID=session-1"));
        Assertions.assertNull(otherHostHeader);

        cookieStore.endSession();
    }

    @Test
    void shouldDropExpiredCookieAndAcceptRefreshedCookie() {
        RemoteSessionCookieStore cookieStore = new RemoteSessionCookieStore();
        cookieStore.beginSession();

        URI sourceUri = URI.create("http://repo-a.local/alfresco/service/becpg/remote/entity");

        HttpHeaders initialHeaders = new HttpHeaders();
        initialHeaders.add(HttpHeaders.SET_COOKIE, "JSESSIONID=session-1; Path=/alfresco; HttpOnly");
        cookieStore.updateFromResponse(sourceUri, initialHeaders);

        HttpHeaders expiredHeaders = new HttpHeaders();
        expiredHeaders.add(HttpHeaders.SET_COOKIE, "JSESSIONID=deleted; Path=/alfresco; Max-Age=0");
        cookieStore.updateFromResponse(sourceUri, expiredHeaders);

        Assertions.assertNull(cookieStore.buildCookieHeader(sourceUri));

        HttpHeaders refreshedHeaders = new HttpHeaders();
        refreshedHeaders.add(HttpHeaders.SET_COOKIE, "JSESSIONID=session-2; Path=/alfresco; HttpOnly");
        cookieStore.updateFromResponse(sourceUri, refreshedHeaders);

        String refreshedHeader = cookieStore.buildCookieHeader(sourceUri);
        Assertions.assertNotNull(refreshedHeader);
        Assertions.assertTrue(refreshedHeader.contains("JSESSIONID=session-2"));

        cookieStore.endSession();
    }
}
