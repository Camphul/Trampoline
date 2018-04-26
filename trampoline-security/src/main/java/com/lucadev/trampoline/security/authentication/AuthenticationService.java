package com.lucadev.trampoline.security.authentication;


import com.lucadev.trampoline.security.model.User;

import java.util.Optional;

/**
 * Interface with method definitions help with authentication of a {@link User}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public interface AuthenticationService<T extends AuthenticationPayload> {

    /**
     * Authenticate a {@link User} from a given {@link AuthenticationPayload}
     *
     * @param authPayload the payload containing required credentials to perform authentication.
     * @return an {@link Optional} containing the authenticated {@link User}
     */
    Optional<User> authenticate(T authPayload);

}
