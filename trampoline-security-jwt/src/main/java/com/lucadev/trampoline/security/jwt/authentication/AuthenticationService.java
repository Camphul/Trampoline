package com.lucadev.trampoline.security.jwt.authentication;


import com.lucadev.trampoline.security.model.User;

/**
 * Interface with methods to authenticate user objects.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 14-4-18
 */
public interface AuthenticationService<T extends AuthenticationPayload> {

    User authenticate(T authPayload);

    /**
     * Get user from current thread
     */
    User getCurrentUser();

}
