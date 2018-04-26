package com.lucadev.trampoline.security.service;

import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.repository.UserRepository;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

/**
 * Abstract implementation of {@link UserService}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public abstract class AbstractUserService implements UserService {

    @Getter(AccessLevel.PROTECTED)
    private final UserRepository userRepository;

    /**
     * Construct the abstract service.
     * @param userRepository the {@code Repository} used to persist {@link User} entities.
     */
    public AbstractUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findOneByUsername(s);
        return user.orElseThrow(() -> new UsernameNotFoundException("Could not find user with username " + s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return Optional.empty();
        }
        Object principal = auth.getPrincipal();
        if (principal == null || !(principal instanceof User)) {
            return Optional.empty();
        }

        return Optional.of((User) principal);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findById(UUID subject) {
        return userRepository.findById(subject).orElse(null);
    }
}
