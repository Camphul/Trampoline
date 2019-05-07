package com.lucadev.trampoline.security.configuration;

import com.lucadev.trampoline.security.authentication.builder.AuthorizationSchemeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NOP implementation of {@link AuthorizationSchemeConfiguration}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7-12-18
 */
public class NOPAuthorizationSchemeConfiguration implements AuthorizationSchemeConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(NOPAuthorizationSchemeConfiguration.class);

	/**
	 * NOP implementation of {@link AuthorizationSchemeConfiguration#build(AuthorizationSchemeBuilder)}
	 *
	 * @param builder the {@link AuthorizationSchemeBuilder}
	 */
	@Override
	public void build(AuthorizationSchemeBuilder builder) {
		LOGGER.debug("No AuthorizationSchemeConfiguration has been defined. Skipping.");
	}
}
