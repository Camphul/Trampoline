package com.lucadev.trampoline.security.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Configuration properties for authentication.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Data
@Validated
@ConfigurationProperties(prefix = "trampoline.security")
public class SecurityConfigurationProperties {

	// True when you want to sign in using email instead of password
	@NotNull
	private boolean allowEmailIdentification = false;

	// Set debug for spring security and trampoline-security
	@NotNull
	private boolean debug = false;

}
