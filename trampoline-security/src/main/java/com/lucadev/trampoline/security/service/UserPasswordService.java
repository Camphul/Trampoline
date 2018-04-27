package com.lucadev.trampoline.security.service;

import com.lucadev.trampoline.security.model.User;

/**
 * Service interface that defines methods required to manage user passwords.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
public interface UserPasswordService {

    /**
     * Is {@code password} the {@link User} password.
     * @param user the {@link User} to check on.
     * @param password the expected password.
     * @return if the password matches.
     */
    boolean isPassword(User user, String password);

    /**
     * Change {@link User} password.
     * @param user
     * @param newPassword
     * @return
     */
    User changePassword(User user, String newPassword);
}
