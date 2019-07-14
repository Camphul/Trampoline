package com.lucadev.trampoline.security.logging.handler.event;

import org.springframework.context.ApplicationListener;

/**
 * Application event listener for user activity events.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7/14/19
 * @see UserActivityEvent
 */
public interface UserActivityEventListener extends ApplicationListener<UserActivityEvent> {

	@Override
	default void onApplicationEvent(UserActivityEvent userActivityEvent) {
		if(filter(userActivityEvent)) {
			handle(userActivityEvent);
		}
	}

	/**
	 * Ability to filter user activities.
	 * @param userActivityEvent the event.
	 * @return true when we want to handle the user activity.
	 */
	default boolean filter(UserActivityEvent userActivityEvent) {
		return true;
	}

	/**
	 * Handle user activity after the filter.
	 * @param event user activity event.
	 * @see UserActivityEvent
	 */
	void handle(UserActivityEvent event);
}
