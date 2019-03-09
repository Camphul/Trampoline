package com.lucadev.trampoline.security.logging.activity.handler;

import com.lucadev.trampoline.security.logging.activity.UserActivity;
import com.lucadev.trampoline.security.logging.activity.UserActivityInvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
		String principalName = "null";
		if(userActivity.getAuthentication() != null) {
			principalName = userActivity.getAuthentication().getName();
		}

		UserActivityInvocationContext context = userActivity.getInvocationContext();

		String identifier = userActivity.getIdentifier();
		LOG.debug("{} triggered user activity {} through method {}#{} which took {}ms to complete",
				principalName, identifier, context.getClassName(), context.getMethodName(),
				(context.getExecutionFinish()-context.getExecutionStart()));
	}
}
