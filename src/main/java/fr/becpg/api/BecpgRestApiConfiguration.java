package fr.becpg.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * <p>BecpgRestApiConfiguration class.</p>
 *
 * @author matthieu
 * @version $Id: $Id
 */
@Configuration
public class BecpgRestApiConfiguration {

	@Value("${content.service.security.basicAuth.username}")
	private String basicAuthUsername;

	@Value("${content.service.security.basicAuth.password}")
	private String basicAuthPassword;

	@Value("${content.service.url}")
	private String contentServiceUrl;
	
	@Value("#{${content.service.headers:{}}}")
	private Map<String,String> customHeaders;

	/**
	 * <p>Getter for the field <code>basicAuthUsername</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getBasicAuthUsername() {
		return basicAuthUsername;
	}

	/**
	 * <p>Getter for the field <code>basicAuthPassword</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getBasicAuthPassword() {
		return basicAuthPassword;
	}

	/**
	 * <p>Getter for the field <code>contentServiceUrl</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getContentServiceUrl() {
		return contentServiceUrl;
	}

	/**
	 * <p>Getter for the field <code>customHeaders</code>.</p>
	 *
	 * @return a {@link java.util.Map} object
	 */
	public Map<String, String> getCustomHeaders() {
		return customHeaders;
	}

	
}
