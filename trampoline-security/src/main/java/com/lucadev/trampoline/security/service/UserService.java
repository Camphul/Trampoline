package com.lucadev.trampoline.security.service;

import com.lucadev.trampoline.security.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service to manage {@link User} entities.
 * Extends {@link UserDetailsService} to implement Spring Security.
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public interface UserService extends UserDetailsService {

    /**
     * Obtain the {@link User} from the current {@link Thread}
     * @return currently active {@link User} inside an {@link Optional}
     */
    Optional<User> currentUser();

    /**
     * Find a {@link User} by it's {@link User#id}
     * @param subject the {@link UUID} to find.
     * @return null or the found {@link User}
     */
    User findById(UUID subject);

    /**
     * Update a {@link User}
     * @param user the {@link User} containing updated data.
     * @return the updated {@link User} after being updated.
     */
    User update(User user);

    /**
     * Update {@link User#updated} value.
     * @param user the {@link User} to update.
     * @return the updated {@link User}
     */
    User updateLastSeen(User user);

    /**
     * Get a {@link List} of all users
     * @return a {@link List} of all {@link User} entities.
     */
    List<User> findAll();

    /**
     * Find all users but paged.
     * @param pageable paging information.
     * @return a {@link Page} of {@link User} entities.
     */
    Page<User> findAll(Pageable pageable);
}
