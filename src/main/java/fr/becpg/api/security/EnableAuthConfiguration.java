package fr.becpg.api.security;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

import fr.becpg.api.RemoteHttpClientFactory;

/**
 * <p>EnableAuthConfiguration class.</p>
 *
 * @author matthieu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({RemoteHttpClientFactory.class, BasicAuthConfiguration.class, OAuth2Configuration.class, DelegatedAuthenticationConfiguration.class})
public @interface EnableAuthConfiguration {

}
