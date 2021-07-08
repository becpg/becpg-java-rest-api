package fr.becpg.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BecpgRestApiConfiguration {

	@Value("${content.service.security.basicAuth.username}")
	private String basicAuthUsername;

	@Value("${content.service.security.basicAuth.password}")
	private String basicAuthPassword;

	@Value("${content.service.url}")
	private String contentServiceUrl;

	public String getBasicAuthUsername() {
		return basicAuthUsername;
	}

	public String getBasicAuthPassword() {
		return basicAuthPassword;
	}

	public String getContentServiceUrl() {
		return contentServiceUrl;
	}

}