package com.lucadev.trampoline.security.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A {@link AuthenticationPayload} which works based on a username/password combination.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 16-4-18
 */
@Getter
@AllArgsConstructor
public class UsernamePasswordAuthenticationPayload implements AuthenticationPayload {

    private String username;
    private String password;

    /**
     * Erase payload data
     */
    @Override
    public void erasePayload() {
        this.username = null;
        this.password = null;
    }
}
