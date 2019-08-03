package com.lucadev.trampoline.notify.email.configuration;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Configuration properties for email(ontop of the required spring mail properties).
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "trampoline.notify.email")
public class EmailConfigurationProperties {

	@Valid
	@NestedConfigurationProperty
	private Defaults defaults;

	/**
	 * Nested configuration properties providing default values.
	 */
	@Data
	public static class Defaults {

		// Default sender address.
		@Email
		private String from = "noreply@localhost";

		// Default template to use.
		@NotBlank
		private String template = "email";

		// Default subject
		@NotBlank
		@Length(min = 1, max = 80)
		private String subject = "Email notification";

	}

}
