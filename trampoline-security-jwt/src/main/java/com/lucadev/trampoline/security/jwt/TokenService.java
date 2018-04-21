package com.lucadev.trampoline.security.jwt;

import com.lucadev.trampoline.security.jwt.model.TokenData;
import com.lucadev.trampoline.security.model.User;

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
     * @param user
     * @return
     */
    String createToken(User user);

    /**
     * Refreshes a token, used when the token is almost expired.
     * @param token
     * @return
     */
    String refreshToken(String token);

    /**
     * Read all data from the token into the given pojo
     * @param token
     * @return
     */
    TokenData getTokenData(String token);

    /**
     * Similar to {@link #getTokenData(String)} but this reads the request header instead of passing the raw token
     * @param request
     * @return
     */
    TokenData getTokenDataFromRequest(HttpServletRequest request);

    /**
     * Validate a token
     * @param tokenData the data read from the token
     * @param user the user to validate the data on
     * @return if the token is valid with the given user
     */
    boolean isValidToken(TokenData tokenData, User user);

    /**
     * Processes a request to refresh a token
     * @param request
     * @return
     */
    String processTokenRefreshRequest(HttpServletRequest request);

}
