package com.lucadev.trampoline.security.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for authentication.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Data
@ConfigurationProperties(prefix = "trampoline.security")
public class SecurityConfigurationProperties {

	//True when you want to sign in using email instead of password
	private boolean emailIdentification = false;

	//Set debug for spring security and trampoline-security
	private boolean debug = false;

}
