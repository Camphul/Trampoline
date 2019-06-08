package com.lucadev.trampoline.security.abac.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Configuration properties for setting up ABAC.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Data
@ConfigurationProperties("trampoline.security.abac")
public class AbacSecurityConfigurationProperties {

	//Configures the json file which contains policies.
	@NestedConfigurationProperty
	private JsonPolicyContainerProperties json = new JsonPolicyContainerProperties();

	//If we should have the json policy definition as parent and import all to JPA.
	private boolean importJson = true;

	@Data
	public static class JsonPolicyContainerProperties {
		//File containing the policies.
		private String filePath = "default-policy.json";
	}
}
