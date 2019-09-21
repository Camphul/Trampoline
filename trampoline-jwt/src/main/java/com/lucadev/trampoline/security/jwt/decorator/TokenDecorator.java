package com.lucadev.trampoline.security.jwt.decorator;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * Decorator interface for handling tokens.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 22-5-18
 */
public interface TokenDecorator {

	/**
	 * Modify the claims before a token is created.
	 * @param user the user for which a token is created
	 * @param claims the created claims
	 */
	void createClaims(UserDetails user, Map<String, Object> claims);

}
