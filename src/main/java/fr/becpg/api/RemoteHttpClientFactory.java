package fr.becpg.api;

import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.Duration;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelOption;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

/**
 * Builds the HTTP clients the SDK uses to reach the repository, so that they all honour the same {@code remote.*} transport settings.
 * <p>
 * Three clients are involved and only the first one goes through the remote {@link org.springframework.web.reactive.function.client.WebClient}: the
 * API calls (reactor-netty), the Alfresco login call of {@code BasicAuthConfiguration} (JDK HTTP client) and the OAuth2 token call of
 * {@code OAuth2Configuration} (its own WebClient). Configuring them from a single place keeps a repository published behind a self-signed or
 * company-signed certificate reachable with {@code remote.ssl.trustAll=true}, whichever authentication is in use.
 *
 * @author matthieu
 */
@Component
public class RemoteHttpClientFactory {

	private static final Log logger = LogFactory.getLog(RemoteHttpClientFactory.class);

	private static final String TLS12_PROTOCOL = "TLSv1.2";

	@Value("${remote.ssl.trustAll:false}")
	private Boolean sslTrustAll;

	@Value("${remote.force.http1:false}")
	private Boolean forceHttp1;

	@Value("${remote.force.tls12:false}")
	private Boolean forceTls12;

	/** Max time (ms) to establish the TCP connection. 0 disables the bound. */
	@Value("${remote.connect.timeout:30000}")
	private Integer connectTimeoutMs;

	/**
	 * Max time (s) to fully receive a response once the request is sent. Guards against a half-open socket (e.g. a satellite link dropping
	 * mid-request) deadlocking the caller, since every API call blocks on the resulting Mono. 0 disables the bound.
	 */
	@Value("${remote.response.timeout:300}")
	private Integer responseTimeoutSeconds;

	/**
	 * <p>shouldDisableSSLVerification.</p>
	 *
	 * @return a boolean
	 */
	public boolean shouldDisableSSLVerification() {
		return Boolean.TRUE.equals(sslTrustAll);
	}

	/**
	 * <p>shouldForceHttp1.</p>
	 *
	 * @return a boolean
	 */
	public boolean shouldForceHttp1() {
		return Boolean.TRUE.equals(forceHttp1);
	}

	/**
	 * <p>shouldForceTls12.</p>
	 *
	 * @return a boolean
	 */
	public boolean shouldForceTls12() {
		return Boolean.TRUE.equals(forceTls12);
	}

	/**
	 * <p>Getter for the field <code>responseTimeoutSeconds</code>.</p>
	 *
	 * @return a {@link java.lang.Integer} object
	 */
	public Integer getResponseTimeoutSeconds() {
		return responseTimeoutSeconds;
	}

	/**
	 * Build a reactor-netty client applying the configured SSL, protocol and timeout settings.
	 *
	 * @param connectionProvider connection provider to use, or {@code null} for the default one
	 * @param clientName client name used in logs
	 * @return a {@link reactor.netty.http.client.HttpClient} object
	 */
	public HttpClient createReactorHttpClient(ConnectionProvider connectionProvider, String clientName) {
		HttpClient httpClient = connectionProvider != null ? HttpClient.create(connectionProvider) : HttpClient.create();

		if (connectTimeoutMs != null && connectTimeoutMs > 0) {
			httpClient = httpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMs);
		}
		if (responseTimeoutSeconds != null && responseTimeoutSeconds > 0) {
			httpClient = httpClient.responseTimeout(Duration.ofSeconds(responseTimeoutSeconds));
			logger.info(clientName + " response timeout set to " + responseTimeoutSeconds + "s, connect timeout " + connectTimeoutMs + "ms");
		}

		if (shouldForceHttp1()) {
			httpClient = httpClient.protocol(HttpProtocol.HTTP11);
			logger.info("HTTP/1.1 forced for " + clientName);
		}

		if (logger.isDebugEnabled()) {
			httpClient.wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
		}

		if (shouldDisableSSLVerification()) {
			return disableSSLVerification(httpClient, clientName);
		}
		if (shouldForceTls12()) {
			return forceTls12(httpClient, clientName);
		}
		logger.debug("SSL verification is enabled for " + clientName);
		return httpClient;
	}

	private HttpClient disableSSLVerification(HttpClient httpClient, String clientName) {
		try {
			SslContextBuilder sslContextBuilder = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE);
			if (shouldForceTls12()) {
				sslContextBuilder = sslContextBuilder.protocols(TLS12_PROTOCOL);
				logger.info("TLSv1.2 forced for " + clientName);
			}
			SslContext sslContext = sslContextBuilder.build();
			logger.warn("SSL verification disabled for " + clientName);
			return httpClient.secure(t -> t.sslContext(sslContext));
		} catch (SSLException e) {
			logger.error("Cannot disable SSL for connection", e);
		}
		return httpClient;
	}

	private HttpClient forceTls12(HttpClient httpClient, String clientName) {
		try {
			SslContext sslContext = SslContextBuilder.forClient().protocols(TLS12_PROTOCOL).build();
			logger.info("TLSv1.2 forced for " + clientName);
			return httpClient.secure(t -> t.sslContext(sslContext));
		} catch (SSLException e) {
			logger.error("Cannot force TLSv1.2 for connection", e);
		}
		return httpClient;
	}

	/**
	 * Build a JDK HTTP client applying the configured SSL, protocol and timeout settings. Used by the calls that cannot go through the reactive stack,
	 * typically because they are made synchronously from within a WebClient filter.
	 *
	 * @param clientName client name used in logs
	 * @return a {@link java.net.http.HttpClient} object
	 */
	public java.net.http.HttpClient createJdkHttpClient(String clientName) {
		java.net.http.HttpClient.Builder builder = java.net.http.HttpClient.newBuilder();

		if (connectTimeoutMs != null && connectTimeoutMs > 0) {
			builder = builder.connectTimeout(Duration.ofMillis(connectTimeoutMs));
		}

		if (shouldForceHttp1()) {
			builder = builder.version(java.net.http.HttpClient.Version.HTTP_1_1);
			logger.info("HTTP/1.1 forced for " + clientName);
		}

		if (shouldForceTls12()) {
			SSLParameters sslParameters = new SSLParameters();
			sslParameters.setProtocols(new String[] { TLS12_PROTOCOL });
			builder = builder.sslParameters(sslParameters);
			logger.info("TLSv1.2 forced for " + clientName);
		}

		if (shouldDisableSSLVerification()) {
			builder = builder.sslContext(createTrustAllSslContext());
			logger.warn("SSL verification disabled for " + clientName);
		}

		return builder.build();
	}

	/**
	 * Build an SSL context trusting every certificate. The trust manager is an {@link javax.net.ssl.X509ExtendedTrustManager} on purpose: the JDK HTTP
	 * client always sets the {@code HTTPS} endpoint identification algorithm, and only an extended trust manager skips both the certificate path
	 * validation and the hostname check.
	 *
	 * @return SSL context accepting any server certificate
	 */
	private javax.net.ssl.SSLContext createTrustAllSslContext() {
		try {
			javax.net.ssl.SSLContext sslContext = javax.net.ssl.SSLContext.getInstance(shouldForceTls12() ? TLS12_PROTOCOL : "TLS");
			sslContext.init(null, new TrustManager[] { trustAllManager() }, new SecureRandom());
			return sslContext;
		} catch (GeneralSecurityException e) {
			throw new IllegalStateException("Cannot disable SSL verification", e);
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

}
