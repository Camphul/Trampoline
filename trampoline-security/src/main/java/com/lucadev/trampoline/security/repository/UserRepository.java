package com.lucadev.trampoline.security.repository;

import com.lucadev.trampoline.data.repository.TrampolineRepository;
import com.lucadev.trampoline.security.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * {@link TrampolineRepository} for {@link User} entities.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Repository
public interface UserRepository extends TrampolineRepository<User> {

    /**
     * Find one {@link User} by it's {@link User#username}
     *
     * @param username the {@link User#username}
     * @return the found {@link User}
     */
    Optional<User> findOneByUsername(String username);

}
