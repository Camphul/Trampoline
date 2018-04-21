package com.lucadev.trampoline.security.service;

import com.lucadev.trampoline.security.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public interface UserService extends UserDetailsService {

    Optional<User> currentUser();

    User findById(UUID subject);

    User updateLastSeen(User user);
}
