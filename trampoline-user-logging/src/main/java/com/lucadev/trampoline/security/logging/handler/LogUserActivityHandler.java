package com.lucadev.trampoline.security.logging.handler;

import com.lucadev.trampoline.security.logging.UserActivity;
import com.lucadev.trampoline.security.logging.UserActivityInvocationDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple {@link UserActivityHandler} which logs activities.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/9/19
 */
public class LogUserActivityHandler implements UserActivityHandler {

	private static final Logger LOG = LoggerFactory
			.getLogger(LogUserActivityHandler.class);

	@Override
	public void handleUserActivity(UserActivity userActivity) {
		UserActivityInvocationDetails context = userActivity.getInvocationDetails();
		String activityContextClass = userActivity.getActedUpon() != null
				? userActivity.getActedUpon().getClass().getName()
				: "No activity context";
		LOG.debug("{}: {}#{} {} :: {} {}ms", userActivity.getPrincipal().getUsername(),
				context.getClassName(), context.getMethodName(), activityContextClass,
				userActivity.getDescription(),
				(context.getInvocationEnd() - context.getInvocationStart()));
	}

}
