package com.lucadev.trampoline.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface for interacting with JWT tokens.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public interface TokenService {

	/**
	 * Create a new token.
	 * @param user user to create token for.
	 * @return jwt token.
	 */
	String issueToken(UserDetails user);

	/**
	 * Refresh an existing token without any checks.
	 * @param token jwt
	 * @return refreshed jwt
	 */
	String issueTokenRefresh(String token);

	/**
	 * Handle a request to refresh a token.
	 * @param request http req
	 * @return jwt token string.
	 */
	String issueTokenRefresh(HttpServletRequest request);

	/**
	 * Validate a token.
	 * @param tokenPayload the data read from the token
	 * @param user the user to validate the data on
	 * @return if the token is valid with the given user
	 */
	boolean isValidToken(TokenPayload tokenPayload, UserDetails user);

}
