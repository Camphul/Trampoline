package com.lucadev.trampoline.security.authentication;


import com.lucadev.trampoline.security.model.User;

import java.util.Optional;

/**
 * Interface with methods to authenticate user objects.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public interface AuthenticationService<T extends AuthenticationPayload> {

    /**
     * Authenticate a user from a given payload
     *
     * @param authPayload
     * @return
     */
    Optional<User> authenticate(T authPayload);

}
