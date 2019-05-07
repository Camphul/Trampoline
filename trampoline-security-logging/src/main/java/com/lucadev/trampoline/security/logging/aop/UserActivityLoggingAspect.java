package com.lucadev.trampoline.security.logging.aop;

import com.lucadev.trampoline.security.logging.ActivityLayer;
import com.lucadev.trampoline.security.logging.LogUserActivity;
import com.lucadev.trampoline.security.logging.UserActivity;
import com.lucadev.trampoline.security.logging.handler.UserActivityHandler;
import com.lucadev.trampoline.service.time.TimeProvider;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Method;

/**
 * Spring AoP aspect to handle method invocations that have the {@link LogUserActivity} annotation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Aspect
@Order(20)
public class UserActivityLoggingAspect implements ApplicationContextAware {

	private final UserActivityHandler userActivityHandler;
	private final TimeProvider timeProvider;
	private ApplicationContext applicationContext;

	public UserActivityLoggingAspect(UserActivityHandler userActivityHandler, TimeProvider timeProvider) {
		this.userActivityHandler = userActivityHandler;
		this.timeProvider = timeProvider;
	}

	@Pointcut("@annotation(com.lucadev.trampoline.security.logging.LogUserActivity)")
	public void logUserActivityDefinition() {
	}

	@Around("logUserActivityDefinition()")
	public Object logUserActivity(ProceedingJoinPoint joinPoint) throws Throwable {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		String className = joinPoint.getSignature().getDeclaringTypeName();
		Object[] methodInvocationArguments = joinPoint.getArgs();

		LogUserActivity logUserActivity = method.getAnnotation(LogUserActivity.class);
		String logIdentifier = logUserActivity.value();
		String category = logUserActivity.category();
		ActivityLayer activityLayer = logUserActivity.layer();

		Object returnObject = null;
		Throwable throwable = null;
		long invocationStart = timeProvider.unix();
		try {
			returnObject = joinPoint.proceed();
		} catch (Throwable t) {
			throwable = t;
		} finally {
			long invocationEnd = timeProvider.unix();
			InterceptedUserActivityInvocationContext interceptedUserActivityInvocationContext = new InterceptedUserActivityInvocationContext(invocationStart, invocationEnd,
					className, method.getName(), methodInvocationArguments, returnObject, throwable != null);

			InterceptedUserActivity interceptedUserActivity = new InterceptedUserActivity(authentication, logIdentifier, category, activityLayer, interceptedUserActivityInvocationContext);
			UserActivity userActivity = applicationContext.getBean(logUserActivity.resolver()).resolveInterceptedUserActivity(interceptedUserActivity);
			userActivityHandler.handleUserActivity(userActivity);
		}

		//Handle exception generated by proxied method
		if (throwable != null) {
			throw throwable;
		}
		return returnObject;
	}

	/**
	 * Invoked by spring context as we require to obtain a resolver bean for user activities.
	 *
	 * @param applicationContext app context.
	 * @throws BeansException when we couldnt set the app context.
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}