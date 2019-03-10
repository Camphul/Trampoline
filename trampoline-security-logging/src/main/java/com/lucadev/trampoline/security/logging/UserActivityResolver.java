package com.lucadev.trampoline.security.logging;

import com.lucadev.trampoline.security.logging.aop.InterceptedUserActivity;

/**
 * Interface used to map user activity.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/10/19
 */
public interface UserActivityResolver {

	/**
	 * Map to a readable UserActivity.
	 * @param activity
	 * @return
	 */
	UserActivity resolveInterceptedUserActivity(InterceptedUserActivity activity);
}
