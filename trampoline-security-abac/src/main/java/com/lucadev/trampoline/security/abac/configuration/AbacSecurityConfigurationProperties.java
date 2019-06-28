package com.lucadev.trampoline.security.abac.configuration;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * Configuration properties for setting up ABAC.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Data
@Validated
@ConfigurationProperties("trampoline.security.abac")
public class AbacSecurityConfigurationProperties {

	@Valid
	@NestedConfigurationProperty
	private PolicyContainerProperties container = new PolicyContainerProperties();

	/**
	 * Configuration used to configure the policy container.
	 */
	@Data
	public static class PolicyContainerProperties {

		// Define which container to use.
		private String provider = "json";

		// Configures the json file which contains policies.
		@Valid
		@NestedConfigurationProperty
		private JsonPolicyContainerProperties json = new JsonPolicyContainerProperties();

		@Valid
		@NestedConfigurationProperty
		private JpaPolicyContainerProperties jpa = new JpaPolicyContainerProperties();

	}

	/**
	 * Inner class for more properties regarding the Json policy container.
	 */
	@Data
	public static class JsonPolicyContainerProperties {

		@Length(min = 1, max = 128)
		// File containing the policies.
		private String filePath = "permissions.json";

	}

	/**
	 * Inner class for more properties regarding the Json policy container.
	 */
	@Data
	public static class JpaPolicyContainerProperties {

		// If we should have the json policy definition as parent and import all to JPA.
		private boolean importFromJson = true;

	}

}
