package com.lucadev.trampoline.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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
	 * Get all token information.
	 * @param token jwt string
	 * @return jwt DTO representation.
	 */
	JwtPayload parseToken(String token);

	/**
	 * Similar to {@link #parseToken(String)} but this reads the request header instead.
	 * of passing the raw token
	 * @param request http req
	 * @return jwt dto.
	 */
	JwtPayload parseToken(HttpServletRequest request);

	/**
	 * Validate a token.
	 * @param jwtPayload the data read from the token
	 * @param user the user to validate the data on
	 * @return if the token is valid with the given user
	 */
	boolean isValidToken(JwtPayload jwtPayload, UserDetails user);

	/**
	 * Read the token and create an {@link Authentication} object from it.
	 * @param request http req
	 * @return auth object.
	 */
	Optional<Authentication> getAuthenticationToken(HttpServletRequest request);

}
