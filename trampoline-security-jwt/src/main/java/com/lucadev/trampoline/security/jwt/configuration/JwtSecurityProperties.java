package com.lucadev.trampoline.security.jwt.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

/**
 * Config values for JWT. Has immutable setters
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ConfigurationProperties(prefix = "trampoline.security.jwt")
public class JwtSecurityProperties {

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
	public static final long TOKEN_TIMEOUT = 3600L;

	/**
	 * Errormessage that gets thrown when you try to modify this object through a setter
	 * method.
	 */
	private static final String IMMUTABILITY_ERROR_MESSAGE = "Cannot set prop of immutable config properties!";

	// Signing secret
	private String secret;

	// Timeout for token invalidation
	private long tokenTimeout;

	/**
	 * Handle default cases if a value has not been set yet.
	 */
	@PostConstruct
	public void init() {
		this.secret = handleDefault(this.secret, DEFAULT_SECRET);
		if (this.tokenTimeout <= 0) {
			this.tokenTimeout = 3600L;
		}
	}

	private String handleDefault(String prop, String defaultValue) {
		if (prop == null || prop.isEmpty()) {
			return defaultValue;
		}
		return prop;
	}

	public void setSecret(String secret) {
		if (this.secret != null) {
			throw new IllegalStateException(IMMUTABILITY_ERROR_MESSAGE);
		}
		this.secret = secret;
	}

	public void setTokenTimeout(long tokenTimeout) {
		if (this.tokenTimeout > 0) {
			throw new IllegalStateException(IMMUTABILITY_ERROR_MESSAGE);
		}
		this.tokenTimeout = tokenTimeout;
	}
}
