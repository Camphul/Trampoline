package com.lucadev.trampoline.security.jwt;

import com.lucadev.trampoline.security.jwt.model.TokenData;
import com.lucadev.trampoline.security.model.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public interface JwtService {

    String createToken(User user);

    String refreshToken(String token);

    TokenData getTokenData(String token);

    TokenData getTokenDataFromRequest(HttpServletRequest request);

    boolean isValidToken(TokenData tokenData, User user);

    String processTokenRefreshRequest(HttpServletRequest request);

}
