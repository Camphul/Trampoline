package com.lucadev.trampoline.security.service.impl;

import com.lucadev.trampoline.data.service.AbstractCrudService;
import com.lucadev.trampoline.security.CurrentUserNotFoundException;
import com.lucadev.trampoline.security.authentication.IdentificationField;
import com.lucadev.trampoline.security.authentication.PersistentUserDetails;
import com.lucadev.trampoline.security.configuration.SecurityConfigurationProperties;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.persistence.repository.UserRepository;
import com.lucadev.trampoline.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

/**
 * {@link com.lucadev.trampoline.security.service.UserService} implementation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Slf4j
public class TrampolineUserService extends AbstractCrudService<User, UserRepository>
		implements UserService {

	private IdentificationField identificationField = IdentificationField.USERNAME;

	/**
	 * Construct the abstract handler.
	 *
	 * @param userRepository                  the {@code Repository} used to persist {@link User} entities.
	 * @param securityConfigurationProperties authentication properties.
	 */
	public TrampolineUserService(UserRepository userRepository,
								 SecurityConfigurationProperties securityConfigurationProperties) {
		super(userRepository);
		if (securityConfigurationProperties.isAllowEmailIdentification()) {
			this.identificationField = IdentificationField.USERNAME_OR_EMAIL;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<User> findByUsername(String username) {
		return getRepository().findOneByUsername(username);
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
		UserDetails principal = ((UserDetails) auth.getPrincipal());
		if (principal == null) {
			return Optional.empty();
		}
		if (principal instanceof PersistentUserDetails) {
			return Optional.of(((PersistentUserDetails) principal).getUser());
		}

		return findByUsername(auth.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User currentUserOrThrow() {
		return currentUser().orElseThrow(CurrentUserNotFoundException::new);
	}

	/**
	 * Delete user by id.
	 *
	 * @param id user id.
	 */
	@Override
	public void deleteById(UUID id) {
		log.debug("Deleting user {}", id);
		super.deleteById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<User> findByEmail(String email) {
		return getRepository().findOneByEmail(email);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User update(User user) {
		user = getRepository().save(user);
		user.refreshAuthorities();
		return user;
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

	@Override
	public Optional<User> findByIdentificationField(String identifier) {
		if (this.identificationField == IdentificationField.USERNAME) {
			return findByUsername(identifier);
		}
		else if (this.identificationField == IdentificationField.USERNAME_OR_EMAIL) {
			return getRepository().findOneByUsernameOrEmail(identifier);
		}
		throw new IllegalStateException(
				"Invalid identification field. Cannot find user.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IdentificationField getIdentificationField() {
		return this.identificationField;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setIdentificationField(IdentificationField identificationField) {
		this.identificationField = identificationField;
	}

}
