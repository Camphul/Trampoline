package com.lucadev.trampoline.security.logging.activity.handler;

import com.lucadev.trampoline.security.logging.activity.UserActivity;

/**
 * Interface which gets fired when user activity is recorded.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
public interface UserActivityHandler {

	/**
	 * Handle user activity advices.
	 * @param userActivity user activity that has been ran.
	 */
	void handleUserActivity(UserActivity userActivity);

}
