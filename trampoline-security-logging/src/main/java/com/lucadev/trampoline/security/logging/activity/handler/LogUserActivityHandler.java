package com.lucadev.trampoline.security.logging.activity.handler;

import com.lucadev.trampoline.security.logging.activity.UserActivityMethodDetails;
import com.lucadev.trampoline.security.logging.activity.UserActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple {@link UserActivityHandler} which logs info.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
public class LogUserActivityHandler implements UserActivityHandler {

	private static final Logger LOG = LoggerFactory.getLogger(LogUserActivityHandler.class);

	@Override
	public void handleUserActivity(UserActivity userActivity) {
		UserActivityMethodDetails context = userActivity.getInvocationDetails();

		String identifier = userActivity.getIdentifier();
		LOG.debug("{} triggered user activity {} through method {}#{} which took {}ms to complete",
				userActivity.getPrincipal(), identifier, context.getClassName(), context.getMethodName(),
				(context.getFinishExecution()-context.getStartExecution()));
		LOG.debug("Message: {}", userActivity.getDescription());
	}
}
