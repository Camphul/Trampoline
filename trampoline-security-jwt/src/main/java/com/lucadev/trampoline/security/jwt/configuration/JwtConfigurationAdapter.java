package com.lucadev.trampoline.security.jwt.configuration;

import com.lucadev.trampoline.security.persistence.entity.User;

import java.util.Map;

/**
 * Interface which helps configure the jwt token, allows to set expiry value for claims
 * etc...
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 22-5-18
 */
public interface JwtConfigurationAdapter {

	/**
	 * Should we ignore token expiry
	 * @param user the user we're creating the token for.
	 * @return value of the expiry ignore claim
	 */
	boolean shouldIgnoreExpiration(User user);

	/**
	 * Modify the claims before a token is created.
	 * @param user the user for which a token is created
	 * @param claims the created claims
	 */
	void createToken(User user, Map<String, Object> claims);

}
