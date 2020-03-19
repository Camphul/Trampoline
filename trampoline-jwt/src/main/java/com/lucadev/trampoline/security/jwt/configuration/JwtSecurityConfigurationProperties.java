package com.lucadev.trampoline.security.jwt.configuration;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Config values for JWT. Has immutable setters
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Data
@Validated
@Configuration
@Primary
@ConfigurationProperties(prefix = "trampoline.security.jwt")
public class JwtSecurityConfigurationProperties {

	/**
	 * Errormessage that gets thrown when you try to modify this object through a setter
	 * method.
	 */
	private static final String IMMUTABILITY_ERROR_MESSAGE = "Cannot set prop of immutable config properties!";

	// Signing secret
	@Length(min = 16)
	private String secret = "trampolineSecret238746982643";

	// Timeout for token invalidation
	@Positive
	private long tokenTimeout = 3600L;

	// Head name
	@NotBlank
	private String header = "Authorization";

	// Schema used for jwt
	@NotNull
	private String headerSchema = "Bearer";

	// Configures claims.
	@NotNull
	private ClaimsConfigurationProperties claims = new ClaimsConfigurationProperties();

	/**
	 * Configures claim keys.
	 */
	@Data
	public static class ClaimsConfigurationProperties {

		// Name of the key containing the principal id.
		@NotBlank
		private String principalIdentifier = "principalId";

		// Name of the key containing the username.
		@NotBlank
		private String username = "username";

		// Name of the key containing the authorities.
		@NotBlank
		private String authorities = "authorities";

	}

}
