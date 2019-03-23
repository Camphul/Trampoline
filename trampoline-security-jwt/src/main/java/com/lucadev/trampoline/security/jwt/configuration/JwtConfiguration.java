package com.lucadev.trampoline.security.jwt.configuration;

import com.lucadev.trampoline.security.model.User;

import java.util.Map;

/**
 * Interface which helps configure the jwt token, allows to set expiry value for claims etc...
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 22-5-18
 */
public interface JwtConfiguration {

    /**
     * Should we ignore token expiry
     *
     * @param user the user we're creating the token for.
     * @return value of the expiry ignore claim
     */
    boolean getIgnoreExpirationFlag(User user);

    /**
     * If we should set impersonate mode to true.
     *
     * @param user the user to auth.
     * @return if we should impersonate.
     */
    boolean getImpersonateFlag(User user);

    /**
     * Modify the claims before a token is created.
     *
     * @param user   the user for which a token is created
     * @param claims the created claims
     */
    void createToken(User user, Map<String, Object> claims);
}
