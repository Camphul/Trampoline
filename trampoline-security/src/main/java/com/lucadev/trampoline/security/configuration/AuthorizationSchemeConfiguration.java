package com.lucadev.trampoline.security.configuration;

import com.lucadev.trampoline.security.authentication.builder.AuthorizationSchemeBuilder;

/**
 * Configuration to create the authorization scheme.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7-12-18
 */
public interface AuthorizationSchemeConfiguration {

	/**
	 * Build the authorization scheme using the Java builder.
	 * @param builder the {@link AuthorizationSchemeBuilder}
	 * @return the builder.
	 */
	AuthorizationSchemeBuilder build(AuthorizationSchemeBuilder builder);

}
