package com.lucadev.trampoline.security.authentication;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;

/**
 * Listener for authentication success.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7/2/19
 */
public interface AuthenticationSuccessListener
		extends ApplicationListener<AuthenticationSuccessEvent> {

	/**
	 * Delegates authentication to {@link #onAuthenticationSuccess(Authentication)}.
	 * @param authenticationSuccessEvent authentication event.
	 */
	@Override
	default void onApplicationEvent(
			AuthenticationSuccessEvent authenticationSuccessEvent) {
		onAuthenticationSuccess(authenticationSuccessEvent.getAuthentication());
	}

	/**
	 * Handle authentication success.
	 * @param authentication the authentication object.
	 */
	void onAuthenticationSuccess(Authentication authentication);

}
