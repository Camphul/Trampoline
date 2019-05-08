package com.lucadev.trampoline.security.logging.handler;

import com.lucadev.trampoline.security.logging.UserActivity;

/**
 * Interface which gets fired when user activity is recorded.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/9/19
 */
public interface UserActivityHandler {

	/**
	 * Handle user activity advices.
	 *
	 * @param userActivity user activity that has been ran.
	 */
	void handleUserActivity(UserActivity userActivity);

}
