package com.lucadev.trampoline.security.jwt.model;

import com.lucadev.trampoline.model.SuccessResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Response after sending an authentication request.
 * Contains the jwt token if the authentication attempt succeeded
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 14-4-18
 */
@Getter
@ToString
@EqualsAndHashCode
public class AuthenticationTokenResponse extends SuccessResponse {

    private final String token;

    public AuthenticationTokenResponse(boolean success, String token) {
        super(success);
        this.token = token;
    }

    public AuthenticationTokenResponse(boolean success, String token, String message) {
        super(success, message);
        this.token = token;
    }

    public AuthenticationTokenResponse(String token) {
        this(token != null && !token.isEmpty(), token, (token != null && !token.isEmpty()) ? null : "Failed auth(no token obtained)");
    }

}
