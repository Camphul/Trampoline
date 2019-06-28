package com.lucadev.trampoline.security.service.impl;

import com.lucadev.trampoline.security.CurrentUserNotFoundException;
import com.lucadev.trampoline.security.authentication.IdentificationField;
import com.lucadev.trampoline.security.configuration.SecurityConfigurationProperties;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.persistence.repository.UserRepository;
import com.lucadev.trampoline.security.service.UserService;
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
	public UserDetails loadUserByUsername(String s) {
		Optional<User> user = Optional.empty();
		if (this.identificationField == IdentificationField.USERNAME) {
			user = this.userRepository.findOneByUsername(s);
		}
		else if (this.identificationField == IdentificationField.USERNAME_OR_EMAIL) {
			user = this.userRepository.findOneByUsernameOrEmail(s);
		}
		return user.orElseThrow(() -> new UsernameNotFoundException(
				"Could not find user with username " + s));
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
		if (!(principal instanceof UserDetails)) {
			return Optional.empty();
		}

		return User.from((UserDetails)principal);
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
	public User findById(UUID subject) {
		return this.userRepository.findById(subject).orElse(null);
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
		return this.userRepository.save(user);
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

	/**
	 * Get the current context's {@link Authentication}.
	 * @return the current thread's
	 * {@link org.springframework.security.core.context.SecurityContext} authentication.
	 */
	private Authentication authenticationContext() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

}
