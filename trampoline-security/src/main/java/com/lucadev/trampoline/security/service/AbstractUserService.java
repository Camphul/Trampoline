package com.lucadev.trampoline.security.service;

import com.lucadev.trampoline.security.CurrentUserNotFoundException;
import com.lucadev.trampoline.security.authentication.IdentificationType;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.persistence.repository.UserRepository;
import lombok.AccessLevel;
import lombok.Getter;
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
 * Abstract implementation of {@link UserService}.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public abstract class AbstractUserService implements UserService {

	@Getter(AccessLevel.PROTECTED)
	private final UserRepository userRepository;

	private IdentificationType identificationType = IdentificationType.USERNAME;

	/**
	 * Construct the abstract handler.
	 * @param userRepository the {@code Repository} used to persist {@link User} entities.
	 * @param emailIdentification if we should use the email attribute for authorization.
	 */
	public AbstractUserService(UserRepository userRepository,
			boolean emailIdentification) {
		this.userRepository = userRepository;
		if (emailIdentification) {
			this.identificationType = IdentificationType.EMAIL;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDetails loadUserByUsername(String s) {
		if (this.identificationType == IdentificationType.EMAIL) {
			return loadUserByEmail(s);
		}
		Optional<User> user = this.userRepository.findOneByUsername(s);
		return user.orElseThrow(() -> new UsernameNotFoundException(
				"Could not find user with username " + s));
	}

	@Override
	public UserDetails loadUserByEmail(String email) {
		Optional<User> user = this.userRepository.findOneByEmail(email);
		return user.orElseThrow(() -> new UsernameNotFoundException(
				"Could not find user with email " + email));
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
		if (!(principal instanceof User)) {
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
	 * Get the current context's {@link Authentication}.
	 * @return the current thread's
	 * {@link org.springframework.security.core.context.SecurityContext} authentication.
	 */
	protected Authentication authenticationContext() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IdentificationType getIdentificationType() {
		return this.identificationType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setIdentificationType(IdentificationType identificationType) {
		this.identificationType = identificationType;
	}

}
