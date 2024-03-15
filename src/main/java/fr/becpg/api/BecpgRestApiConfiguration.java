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

	@Value("${content.service.url:}")
	private String contentServiceUrl;
	
	@Value("#{${content.service.headers:{}}}")
	private Map<String,String> customHeaders;

	
	@Value("${remote.compress.param:false}")
	private Boolean compressParam;
	
	
	@Value("${remote.ssl.trustAll:false}")
	private Boolean sslTrustAll;
	
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

	
	public boolean shouldCompressParam() {
		return Boolean.TRUE.equals(compressParam);
	}

	public boolean shouldDisableSSLVerification() {
		return Boolean.TRUE.equals(sslTrustAll);
	}
	
}
