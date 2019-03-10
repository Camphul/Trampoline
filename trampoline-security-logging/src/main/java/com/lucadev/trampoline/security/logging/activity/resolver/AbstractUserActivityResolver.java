package com.lucadev.trampoline.security.logging.activity.resolver;

import com.lucadev.trampoline.security.logging.ActivityLayer;
import com.lucadev.trampoline.security.logging.activity.UserActivity;
import com.lucadev.trampoline.security.logging.activity.UserActivityMethodDetails;
import com.lucadev.trampoline.security.logging.activity.UserActivityResolver;
import com.lucadev.trampoline.security.logging.aop.InterceptedUserActivity;
import com.lucadev.trampoline.security.logging.aop.InterceptedUserActivityInvocationContext;
import com.lucadev.trampoline.security.model.User;
import org.springframework.security.core.Authentication;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/10/19
 */
public abstract class AbstractUserActivityResolver implements UserActivityResolver {

	@Override
	public UserActivity resolveInterceptedUserActivity(InterceptedUserActivity activity) {
		Authentication auth = activity.getAuthentication();
		String principal = null;
		if(auth != null) {
			if(auth.getPrincipal() instanceof User) {
				principal = String.valueOf(((User)auth.getPrincipal()).getId());
			} else {
				principal = auth.getName();
			}
		}

		String identifier = activity.getIdentifier();
		String category = activity.getCategory();
		ActivityLayer activityLayer = activity.getActivityLayer();
		String description = getReadableDescription(activity);
		InterceptedUserActivityInvocationContext ctx = activity.getInvocationContext();
		UserActivityMethodDetails mid = resolveInvocationDetails(ctx);

		return new UserActivity(principal, identifier, category, activityLayer, mid, description);
	}

	protected UserActivityMethodDetails resolveInvocationDetails(InterceptedUserActivityInvocationContext ctx) {
		String className = ctx.getClassName();
		String methodName = ctx.getMethodName();
		boolean exceptionThrown = ctx.isExceptionThrown();
		long startExec = ctx.getExecutionStart();
		long finishExec = ctx.getExecutionFinish();
		return new UserActivityMethodDetails(className, methodName, exceptionThrown, startExec, finishExec);
	}

	/**
	 * Obtain a readable message.
	 * @param userActivity
	 * @return
	 */
	public abstract String getReadableDescription(InterceptedUserActivity userActivity);
}
