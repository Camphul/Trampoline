package com.lucadev.trampoline.security.jwt.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Config values for JWT. Has immutable setters
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "trampoline.security.jwt")
public class JwtSecurityConfigurationProperties {

	/**
	 * Default secret if none are defined.
	 */
	public static final String DEFAULT_SECRET = "trampolineSecret238746982643";

	/**
	 * Header containing our token.
	 */
	public static final String TOKEN_HEADER = "Authorization";

	/**
	 * Define scheme used in header.
	 */
	public static final String HEADER_PREFIX = "Bearer";

	/**
	 * Default timeout for jwt tokens(expiry).
	 */
	public static final long DEFAULT_TOKEN_TIMEOUT = 3600L;

	/**
	 * Errormessage that gets thrown when you try to modify this object through a setter
	 * method.
	 */
	private static final String IMMUTABILITY_ERROR_MESSAGE = "Cannot set prop of immutable config properties!";

	// Signing secret
	private String secret = DEFAULT_SECRET;

	// Timeout for token invalidation
	private long tokenTimeout = DEFAULT_TOKEN_TIMEOUT;

}
