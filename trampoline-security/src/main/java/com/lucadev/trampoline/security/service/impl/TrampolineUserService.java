package com.lucadev.trampoline.security.service.impl;

import com.lucadev.trampoline.security.configuration.SecurityConfigurationProperties;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.persistence.repository.UserRepository;
import com.lucadev.trampoline.security.service.AbstractUserService;
import com.lucadev.trampoline.service.time.TimeProvider;

/**
 * {@link com.lucadev.trampoline.security.service.UserService} implementation. Uses a
 * {@link TimeProvider} for {@link TrampolineUserService#updateLastSeen(User)}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class TrampolineUserService extends AbstractUserService {

	private final TimeProvider timeProvider;

	/**
	 * Construct the handler.
	 * @param userRepository the repository to persist {@link User} entities.
	 * @param timeProvider {@link TimeProvider} instance.
	 * @param securityConfigurationProperties properties which define how the user service behaves.
	 */
	public TrampolineUserService(UserRepository userRepository, TimeProvider timeProvider,
								 SecurityConfigurationProperties securityConfigurationProperties) {
		super(userRepository, securityConfigurationProperties);
		this.timeProvider = timeProvider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User updateLastSeen(User user) {
		user.setLastSeen(this.timeProvider.now());
		return update(user);
	}

}
