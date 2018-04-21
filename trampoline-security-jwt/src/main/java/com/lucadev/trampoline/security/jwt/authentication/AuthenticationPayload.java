package com.lucadev.trampoline.security.jwt.authentication;

/**
 * Token used by {@link AuthenticationService} to decouple from username password auth
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 16-4-18
 */
public interface AuthenticationPayload {

    /**
     * Erase payload data
     */
    void eraseCredentials();
}
