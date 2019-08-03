package com.lucadev.trampoline.security.service.impl;

import com.lucadev.trampoline.security.CurrentUserNotFoundException;
import com.lucadev.trampoline.security.authentication.IdentificationField;
import com.lucadev.trampoline.security.authentication.SimpleUserDetails;
import com.lucadev.trampoline.security.configuration.SecurityConfigurationProperties;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.persistence.repository.UserRepository;
import com.lucadev.trampoline.security.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * {@link com.lucadev.trampoline.security.service.UserService} implementation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class TrampolineUserService implements UserService {

	private final UserRepository userRepository;

	private IdentificationField identificationField = IdentificationField.USERNAME;

	/**
	 * Construct the abstract handler.
	 * @param userRepository the {@code Repository} used to persist {@link User} entities.
	 * @param securityConfigurationProperties authentication properties.
	 */
	public TrampolineUserService(UserRepository userRepository,
			SecurityConfigurationProperties securityConfigurationProperties) {
		this.userRepository = userRepository;
		if (securityConfigurationProperties.isAllowEmailIdentification()) {
			this.identificationField = IdentificationField.USERNAME_OR_EMAIL;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findOneByUsername(username);
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
		if (principal instanceof SimpleUserDetails) {
			return Optional.of(((SimpleUserDetails) principal).getUser());
		}

		return this.userRepository.findOneByUsername(auth.getName());
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
	public Optional<User> findById(UUID subject) {
		return this.userRepository.findById(subject);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<User> findByEmail(String email) {
		return this.userRepository.findOneByEmail(email);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User save(User user) {
		return this.userRepository.save(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User update(User user) {
		user = this.userRepository.save(user);
		user.refreshAuthorities();
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<User> findAll(Pageable pageable) {
		return this.userRepository.findAll(pageable);
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
		} else if (this.identificationField == IdentificationField.USERNAME_OR_EMAIL) {
			return this.userRepository.findOneByUsernameOrEmail(identifier);
		}
		throw new IllegalStateException("Invalid identification field. Cannot find user.");
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
