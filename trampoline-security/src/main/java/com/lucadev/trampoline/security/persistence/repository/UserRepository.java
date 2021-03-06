package com.lucadev.trampoline.security.persistence.repository;

import com.lucadev.trampoline.data.gdpr.PersonalData;
import com.lucadev.trampoline.data.repository.TrampolineRepository;
import com.lucadev.trampoline.security.persistence.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * {@link TrampolineRepository} for {@link User} entities.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Repository
public interface UserRepository extends TrampolineRepository<User> {

	/**
	 * Find one {@link User} by it's {@link User#getUsername()}.
	 * @param username the {@link User#getUsername()}
	 * @return the found {@link User}
	 */
	Optional<User> findOneByUsername(@PersonalData String username);

	/**
	 * Find user by its email.
	 * @param email user email
	 * @return resolved user in an optional.
	 */
	Optional<User> findOneByEmail(@PersonalData String email);

	/**
	 * Find user by username or by email.
	 * @param identifier identifier being username or email.
	 * @return resolved user in an optional.
	 */
	@Query("from User u where u.username = :identifier or u.email = :identifier")
	Optional<User> findOneByUsernameOrEmail(@PersonalData String identifier);

}
