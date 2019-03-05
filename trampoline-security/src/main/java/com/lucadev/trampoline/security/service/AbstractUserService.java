package com.lucadev.trampoline.security.service;

import com.lucadev.trampoline.security.configuration.AuthenticationProperties;
import com.lucadev.trampoline.security.exception.CurrentUserNotFoundException;
import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.repository.UserRepository;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
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

    protected static final String CACHE_REGION = "trampoline_users_cache";
    @Getter(AccessLevel.PROTECTED)
    private final UserRepository userRepository;
    @Getter(AccessLevel.PROTECTED)
    private final AuthenticationProperties authenticationProperties;

    /**
     * Construct the abstract service.
     *
     * @param userRepository the {@code Repository} used to persist {@link User} entities.
     */
    public AbstractUserService(UserRepository userRepository, AuthenticationProperties authenticationProperties) {
        this.userRepository = userRepository;
        this.authenticationProperties = authenticationProperties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(CACHE_REGION)
    public UserDetails loadUserByUsername(String s) {
        Optional<User> user = userRepository.findOneByUsername(s);
        return user.orElseThrow(() -> new BadCredentialsException("Could not find user with username " + s));
    }

    @Override
    public UserDetails loadUserByIdentifier(String identifier) {
        if(authenticationProperties.isEmailIdentification()){
            return loadUserByEmail(identifier);
        }
        return loadUserByUsername(identifier);
    }

    @Override
    @Cacheable(CACHE_REGION)
    public UserDetails loadUserByEmail(String email) {
        Optional<User> user = userRepository.findOneByEmail(email);
        return user.orElseThrow(() -> new BadCredentialsException("Could not find user with email " + email));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> currentUser() {
        Authentication auth = authenticationContext();
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
    public User currentUserOrThrow() {
        return currentUser().orElseThrow(CurrentUserNotFoundException::new);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(CACHE_REGION)
    public User findById(UUID subject) {
        return userRepository.findById(subject).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CacheEvict(CACHE_REGION)
    public User update(User user) {
        return userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(CACHE_REGION)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(CACHE_REGION)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User disable(User user) {
        return setEnabled(user, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User enable(User user) {
        return setEnabled(user, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User setEnabled(User user, boolean enabled) {
        user.setEnabled(enabled);
        return update(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User expire(User user) {
        return setExpired(user, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User setExpired(User user, boolean expired) {
        user.setExpired(expired);
        return update(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User lock(User user) {
        return setLocked(user, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User unlock(User user) {
        return setLocked(user, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User setLocked(User user, boolean locked) {
        user.setLocked(locked);
        return update(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User expireCredentials(User user) {
        return setCredentialsExpired(user, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User setCredentialsExpired(User user, boolean expired) {
        user.setCredentialsExpired(expired);
        return update(user);
    }

    /**
     * Get the current context's {@link Authentication}
     *
     * @return the current thread's {@link org.springframework.security.core.context.SecurityContext} authentication.
     */
    protected Authentication authenticationContext() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
