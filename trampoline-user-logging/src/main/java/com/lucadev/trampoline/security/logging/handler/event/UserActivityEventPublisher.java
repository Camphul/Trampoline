package com.lucadev.trampoline.security.logging.handler.event;

import com.lucadev.trampoline.security.logging.UserActivity;
import com.lucadev.trampoline.security.logging.UserActivityInvocationContext;
import com.lucadev.trampoline.security.logging.handler.UserActivityHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Application event publisher for user activity events.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7/14/19
 */
@Slf4j
@RequiredArgsConstructor
public class UserActivityEventPublisher implements UserActivityHandler {

	private final ApplicationEventPublisher applicationEventPublisher;

	/**
	 * Publish user activity as an application event.
	 * @param userActivity user activity that has been ran.
	 * @see ApplicationEventPublisher
	 * @see org.springframework.context.ApplicationEvent
	 */
	@Override
	public void handleUserActivity(UserActivity userActivity) {
		UserActivityInvocationContext context = userActivity.getInvocationContext();
		String activityContextClass = userActivity.getActedUpon() != null
				? userActivity.getActedUpon().getClass().getName()
				: "No activity context";
		log.debug("Publishing user activity event {}: {}#{} {} :: {} {}ms",
				userActivity.getPrincipal().getUsername(), context.getClassName(),
				context.getMethodName(), activityContextClass,
				userActivity.getDescription(),
				(context.getInvocationEnd() - context.getInvocationStart()));

		UserActivityEvent event = new UserActivityEvent(context, userActivity);
		this.applicationEventPublisher.publishEvent(event);
	}

}
