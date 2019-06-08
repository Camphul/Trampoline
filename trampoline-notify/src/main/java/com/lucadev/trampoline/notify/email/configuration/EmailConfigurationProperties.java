package com.lucadev.trampoline.notify.email.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for email(ontop of the required spring mail properties).
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "trampoline.notify.email")
public class EmailConfigurationProperties {

	// If we should even load email.
	private boolean enabled = false;

	@NestedConfigurationProperty
	private Defaults defaults;

	@Data
	public static class Defaults {

		// Default sender address.
		private String from = "noreply@localhost";

		// Default template to use.
		private String template = "email";

		// Default subject
		private String subject = "Email notification";

	}

}
