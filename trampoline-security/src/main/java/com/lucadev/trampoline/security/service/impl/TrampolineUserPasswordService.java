package com.lucadev.trampoline.security.service.impl;

import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.service.UserPasswordService;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * {@link UserPasswordService} implementation.
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@AllArgsConstructor
public class TrampolineUserPasswordService implements UserPasswordService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPassword(User user, String password) {
        return passwordEncoder.matches(user.getPassword(), password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        return userService.update(user);
    }
}
