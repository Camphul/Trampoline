package com.lucadev.trampoline.security.service;

import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.repository.UserRepository;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Abstract implementation of {@link UserService}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public abstract class AbstractUserService implements UserService {

    protected static final String CACHE_REGION = "trampoline_cache_users";
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
    @Cacheable(value = CACHE_REGION, key = "#subject")
    public User findById(UUID subject) {
        return userRepository.findById(subject).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CacheEvict(value = CACHE_REGION, key = "#user.id")
    public User update(User user) {
        return userRepository.save(user);
    }

    /**
     * Get a {@link List} of all users
     *
     * @return a {@link List} of all {@link User} entities.
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
