package com.lucadev.trampoline.security.jwt.decorator;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * Default {@link TokenDecorator} implementation which does not do anything.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 22-5-18
 */
public class NopTokenDecorator implements TokenDecorator {

	@Override
	public void createToken(UserDetails user, Map<String, Object> claims) {
		// Dont do anything by default
	}

}
