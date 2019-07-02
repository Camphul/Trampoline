package com.lucadev.trampoline.security.authentication;

import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.service.time.TimeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

/**
 * Update user last seen data through an event listener.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7/2/19
 */
@Slf4j
@RequiredArgsConstructor
public class LastSeenUpdateListener implements AuthenticationSuccessListener {

	private final UserService userService;

	private final TimeProvider timeProvider;

	/**
	 * Updates list seen data.
	 * @param authentication the authentication object.
	 */
	@Override
	public void onAuthenticationSuccess(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (!(principal instanceof User)) {
			log.debug("Skipping lastSeen listener because principal is not a user.");
			return;
		}

		User user = (User) principal;
		user.setLastSeen(this.timeProvider.now());
		this.userService.update(user);
		log.debug("Updated lastSeen for user {}", user.getId());
	}

}
