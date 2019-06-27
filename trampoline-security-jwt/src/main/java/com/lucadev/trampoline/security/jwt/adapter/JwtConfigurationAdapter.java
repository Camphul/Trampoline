package com.lucadev.trampoline.security.jwt.adapter;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * Interface which helps configure the jwt token, allows to set expiry value for claims.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 22-5-18
 */
public interface JwtConfigurationAdapter {

	/**
	 * Should we ignore token expiry.
	 * @param user the user we're creating the token for.
	 * @return value of the expiry ignore claim
	 */
	boolean shouldIgnoreExpiration(UserDetails user);

	/**
	 * Modify the claims before a token is created.
	 * @param user the user for which a token is created
	 * @param claims the created claims
	 */
	void createToken(UserDetails user, Map<String, Object> claims);

}
