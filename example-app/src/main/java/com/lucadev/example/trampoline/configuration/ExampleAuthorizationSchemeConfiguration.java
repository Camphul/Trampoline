package com.lucadev.example.trampoline.configuration;

import com.lucadev.trampoline.security.authentication.builder.AuthorizationSchemeBuilder;
import com.lucadev.trampoline.security.configuration.AuthorizationSchemeConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * Example on how you may setup a role/privilege scheme using trampoline.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.jpa.hibernate", name = "ddl-auto",
		havingValue = "create") // Only run when db scheme is dropped at start
public class ExampleAuthorizationSchemeConfiguration
		implements AuthorizationSchemeConfiguration {

	/**
	 * Configure auth scheme
	 * @param builder
	 * @return
	 */
	@Override
	public AuthorizationSchemeBuilder build(AuthorizationSchemeBuilder builder) {
		return builder.createRole("ROLE_USER")
				.withPrivileges("GET_WHO_AM_I", "GET_PING_PROTECTED").buildAnd()
				.createRole("ROLE_ADMIN").withExistingRolePrivileges("ROLE_USER")
				.withPrivilege("MANAGE_USERS").buildAnd();
	}

}
