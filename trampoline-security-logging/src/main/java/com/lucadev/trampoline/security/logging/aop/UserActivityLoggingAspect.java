package com.lucadev.trampoline.security.logging.aop;

import com.lucadev.trampoline.security.logging.ActivityLayer;
import com.lucadev.trampoline.security.logging.LogUserActivity;
import com.lucadev.trampoline.security.logging.activity.UserActivity;
import com.lucadev.trampoline.security.logging.activity.handler.UserActivityHandler;
import com.lucadev.trampoline.security.logging.activity.UserActivityInvocationContext;
import com.lucadev.trampoline.service.time.TimeProvider;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Method;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Aspect
@AllArgsConstructor
public class UserActivityLoggingAspect {

	private final UserActivityHandler userActivityHandler;
	private final TimeProvider timeProvider;

	@Pointcut("@annotation(com.lucadev.trampoline.security.logging.LogUserActivity)")
	public void logUserActivityDefinition() {
	}

	@Around("logUserActivityDefinition()")
	public Object logUserActivity(ProceedingJoinPoint joinPoint) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		String className = joinPoint.getSignature().getDeclaringTypeName();
		Object[] methodInvocationArguments = joinPoint.getArgs();

		LogUserActivity logUserActivity = method.getAnnotation(LogUserActivity.class);
		String logIdentifier = logUserActivity.value();
		String category = logUserActivity.category();
		ActivityLayer activityLayer = logUserActivity.layer();
		boolean exceptionThrown = false;
		long executionStart = timeProvider.unix();
		Object returnObject = null;
		try {
			returnObject = joinPoint.proceed();
		} catch (Throwable throwable) {
			exceptionThrown = true;
			throwable.printStackTrace();
		} finally {
			long executionEnd = timeProvider.unix();
			UserActivityInvocationContext userActivityInvocationContext = new UserActivityInvocationContext(executionStart, executionEnd,
					className, method.getName(), methodInvocationArguments, returnObject, exceptionThrown);

			UserActivity userActivity = new UserActivity(authentication, logIdentifier, category, activityLayer, userActivityInvocationContext);

			userActivityHandler.handleUserActivity(userActivity);
		}
		return returnObject;
	}

}
