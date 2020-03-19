package com.lucadev.trampoline.security.service;

import com.lucadev.trampoline.data.service.CrudService;
import com.lucadev.trampoline.security.CurrentUserNotFoundException;
import com.lucadev.trampoline.security.authentication.IdentificationField;
import com.lucadev.trampoline.security.persistence.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

/**
 * Service to manage {@link User} entities. Extends {@link UserDetailsService} to
 * implement Spring Security.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public interface UserService extends CrudService<User> {

	/**
	 * Obtain the {@link User} from the current {@link Thread}.
	 *
	 * @return currently active {@link User} inside an {@link Optional}
	 */
	Optional<User> currentUser();

	/**
	 * Obtains the {@link User} from the current {@link Thread}. Throws a
	 * {@link CurrentUserNotFoundException} when null.
	 * @return currently active {@link User}
	 */
	User currentUserOrThrow();

	/**
	 * Find a {@link User} by its username.
	 * @param username the user's username.
	 * @return the found user.
	 */
	Optional<User> findByUsername(String username);

	/**
	 * Find a {@link User} by its email.
	 * @param email the user email.
	 * @return the found user.
	 */
	Optional<User> findByEmail(String email);

	/**
	 * Set user enabled to false.
	 * @param user the {@link User} to disable.
	 * @return the updated, now disabled {@link User}
	 */
	User disable(User user);

	/**
	 * Set user enabled to true.
	 * @param user the {@link User} to enable.
	 * @return the updated, enabled {@link User}
	 */
	User enable(User user);

	/**
	 * Set the user enabled flag.
	 * @param user the {@link User} to set the flag of.
	 * @param enabled enabled flag
	 * @return the updated {@link User}
	 */
	User setEnabled(User user, boolean enabled);

	/**
	 * Expire a {@link User}.
	 * @param user the {@link User} to expire.
	 * @return the updated, expired {@link User}
	 */
	User expire(User user);

	/**
	 * Set the user expired flag.
	 * @param user the {@link User} to set the flag on.
	 * @param expired the new flag value.
	 * @return the updated {@link User}
	 */
	User setExpired(User user, boolean expired);

	/**
	 * Lock a {@link User}.
	 * @param user the {@link User} to lock.
	 * @return the updated {@link User}
	 */
	User lock(User user);

	/**
	 * Unlock a {@link User}.
	 * @param user the {@link User} to unlock.
	 * @return the updated {@link User}
	 */
	User unlock(User user);

	/**
	 * Set the user locked flag.
	 * @param user the {@link User} to apply the flag on.
	 * @param locked the new locked value.
	 * @return the updated {@link User}
	 */
	User setLocked(User user, boolean locked);

	/**
	 * Expires the {@link User} credentials.
	 * @param user the {@link User} to expire the credentials on.
	 * @return the updated {@link User}
	 */
	User expireCredentials(User user);

	/**
	 * Set the user credentials expired flag.
	 * @param user the {@link User} to apply the flag on.
	 * @param expired the new flag value.
	 * @return the updated {@link User}
	 */
	User setCredentialsExpired(User user, boolean expired);

	/**
	 * Find user by identifier which is based on the {@link IdentificationField}.
	 * @param identifier user identifier.
	 * @return the user.
	 */
	Optional<User> findByIdentificationField(String identifier);

	/**
	 * Current {@link IdentificationField} being used.
	 * @return method of identification(username/email)
	 */
	IdentificationField getIdentificationField();

	/**
	 * Set a new {@link IdentificationField}.
	 * @param identificationField if we use username or email for authorization.
	 */
	void setIdentificationField(IdentificationField identificationField);

}
