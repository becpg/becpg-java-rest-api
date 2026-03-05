package fr.becpg.api.security;

import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * Store HTTP session cookies for remote API calls within a doInSession execution scope.
 */
@Component
public class RemoteSessionCookieStore {

    private final Map<String, StoredCookie> cookies = new ConcurrentHashMap<>();

    private final AtomicInteger sessionDepth = new AtomicInteger(0);

    /**
     * Start a session scope.
     */
    public void beginSession() {
        if (sessionDepth.getAndIncrement() == 0) {
            cookies.clear();
        }
    }

    /**
     * End a session scope.
     */
    public void endSession() {
        int depth = sessionDepth.decrementAndGet();
        if (depth <= 0) {
            sessionDepth.set(0);
            cookies.clear();
        }
    }

    /**
     * Return true when a session cookie is available.
     *
     * @return true when at least one cookie is stored
     */
    public boolean hasCookies(URI requestUri) {
        return buildCookieHeader(requestUri) != null;
    }

    /**
     * Build the value of Cookie header.
     *
     * @return Cookie header value or null when no cookie exists
     */
    public String buildCookieHeader(URI requestUri) {
        pruneExpiredCookies();

        if (cookies.isEmpty() || requestUri == null || requestUri.getHost() == null) {
            return null;
        }

        List<StoredCookie> matchingCookies = findMatchingCookies(requestUri);
        if (matchingCookies.isEmpty()) {
            return null;
        }

        matchingCookies.sort(Comparator.comparing(StoredCookie::path));

        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (StoredCookie storedCookie : matchingCookies) {
            if (!first) {
                builder.append("; ");
            }
            builder.append(storedCookie.cookie().getName()).append("=").append(storedCookie.cookie().getValue());
            first = false;
        }
        return builder.toString();
    }

    /**
     * Read Set-Cookie response headers and update stored cookies.
     *
     * @param responseHeaders response headers
     */
    public void updateFromResponse(URI requestUri, HttpHeaders responseHeaders) {
        pruneExpiredCookies();

        if (requestUri == null || requestUri.getHost() == null) {
            return;
        }

        List<String> setCookieHeaders = responseHeaders.get(HttpHeaders.SET_COOKIE);
        if (setCookieHeaders == null || setCookieHeaders.isEmpty()) {
            return;
        }

        String requestHost = normalizeHost(requestUri.getHost());
        String requestPath = normalizePath(requestUri.getPath());

        for (String setCookieHeader : setCookieHeaders) {
            List<HttpCookie> parsedCookies = HttpCookie.parse(setCookieHeader);
            for (HttpCookie parsedCookie : parsedCookies) {
                String cookieDomain = normalizeCookieDomain(parsedCookie.getDomain());
                String cookiePath = normalizeCookiePath(parsedCookie.getPath(), requestPath);
                boolean hostOnly = (cookieDomain == null);
                String domainScope = hostOnly ? requestHost : cookieDomain;
                String key = buildCookieKey(domainScope, cookiePath, parsedCookie.getName(), hostOnly);

                if (parsedCookie.hasExpired() || parsedCookie.getMaxAge() == 0L) {
                    cookies.remove(key);
                } else {
                    cookies.put(key, new StoredCookie(parsedCookie, domainScope, cookiePath, hostOnly));
                }
            }
        }
    }

    private List<StoredCookie> findMatchingCookies(URI requestUri) {
        List<StoredCookie> matchingCookies = new ArrayList<>();
        String requestHost = normalizeHost(requestUri.getHost());
        String requestPath = normalizePath(requestUri.getPath());

        for (StoredCookie storedCookie : cookies.values()) {
            if (!domainMatches(storedCookie, requestHost)) {
                continue;
            }
            if (!pathMatches(storedCookie.path(), requestPath)) {
                continue;
            }
            if (storedCookie.cookie().hasExpired()) {
                continue;
            }
            matchingCookies.add(storedCookie);
        }

        return matchingCookies;
    }

    private boolean domainMatches(StoredCookie storedCookie, String requestHost) {
        if (storedCookie.hostOnly()) {
            return requestHost.equals(storedCookie.domainScope());
        }
        return requestHost.equals(storedCookie.domainScope()) || requestHost.endsWith("." + storedCookie.domainScope());
    }

    private boolean pathMatches(String cookiePath, String requestPath) {
        if (cookiePath == null || cookiePath.isBlank() || "/".equals(cookiePath)) {
            return true;
        }
        return requestPath.startsWith(cookiePath);
    }

    private String normalizeHost(String host) {
        return host == null ? null : host.toLowerCase();
    }

    private String normalizePath(String path) {
        if (path == null || path.isBlank()) {
            return "/";
        }
        return path;
    }

    private String normalizeCookieDomain(String domain) {
        if (domain == null || domain.isBlank()) {
            return null;
        }
        String normalized = domain.toLowerCase();
        if (normalized.startsWith(".")) {
            return normalized.substring(1);
        }
        return normalized;
    }

    private String normalizeCookiePath(String cookiePath, String requestPath) {
        if (cookiePath == null || cookiePath.isBlank()) {
            return requestPath;
        }
        return cookiePath;
    }

    private String buildCookieKey(String domainScope, String path, String name, boolean hostOnly) {
        return String.join("|", hostOnly ? "HOST" : "DOMAIN", domainScope, path, name);
    }

    private void pruneExpiredCookies() {
        if (cookies.isEmpty()) {
            return;
        }

        for (Map.Entry<String, StoredCookie> cookieEntry : cookies.entrySet()) {
            StoredCookie storedCookie = cookieEntry.getValue();
            if (storedCookie.cookie().hasExpired()) {
                cookies.remove(cookieEntry.getKey());
            }
        }
    }

    private record StoredCookie(HttpCookie cookie, String domainScope, String path, boolean hostOnly) {
    }
}
