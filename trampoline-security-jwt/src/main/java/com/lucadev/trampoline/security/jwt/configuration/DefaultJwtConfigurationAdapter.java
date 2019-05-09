package com.lucadev.trampoline.security.jwt.configuration;

import com.lucadev.trampoline.security.persistence.entity.User;

import java.util.Map;

/**
 * Default Jwt configuration.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 22-5-18
 */
public class DefaultJwtConfigurationAdapter implements JwtConfigurationAdapter {

	@Override
	public boolean shouldIgnoreExpiration(User user) {
		return false;
	}

	@Override
	public void createToken(User user, Map<String, Object> claims) {
		// Dont do anything by default
	}

}
