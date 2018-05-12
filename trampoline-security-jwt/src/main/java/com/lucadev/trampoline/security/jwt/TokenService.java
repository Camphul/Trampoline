package com.lucadev.trampoline.security.jwt;

import com.lucadev.trampoline.security.model.User;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface for interacting with JWT tokens
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public interface TokenService {

    /**
     * Creates a JWT token for the given user
     *
     * @param user
     * @return
     */
    String createToken(User user);

    /**
     * Refreshes a token, used when the token is almost expired.
     *
     * @param token
     * @return
     */
    String refreshToken(String token);

    /**
     * Read all data from the token into the given pojo
     *
     * @param token
     * @return
     */
    JwtPayload getTokenData(String token);

    /**
     * Similar to {@link #getTokenData(String)} but this reads the request header instead of passing the raw token
     *
     * @param request
     * @return
     */
    JwtPayload getTokenDataFromRequest(HttpServletRequest request);

    /**
     * Validate a token
     *
     * @param jwtPayload the data read from the token
     * @param user       the user to validate the data on
     * @return if the token is valid with the given user
     */
    boolean isValidToken(JwtPayload jwtPayload, User user);

    /**
     * Processes a request to refresh a token
     *
     * @param request
     * @return
     */
    String processTokenRefreshRequest(HttpServletRequest request);

    /**
     * Get authentication token from request(not authenticated)
     *
     * @param request
     * @return
     */
    Authentication getAuthenticationToken(HttpServletRequest request);

}
