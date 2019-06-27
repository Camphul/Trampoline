package com.lucadev.trampoline.security.jwt.adapter;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * Default {@link JwtConfigurationAdapter} implementation which does not do anything.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 22-5-18
 */
public class NopJwtConfigurationAdapter implements JwtConfigurationAdapter {

	@Override
	public boolean shouldIgnoreExpiration(UserDetails user) {
		return false;
	}

	@Override
	public void createToken(UserDetails user, Map<String, Object> claims) {
		// Dont do anything by default
	}

}
