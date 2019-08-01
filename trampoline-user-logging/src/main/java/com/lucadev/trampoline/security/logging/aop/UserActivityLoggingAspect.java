package com.lucadev.trampoline.security.logging.aop;

import com.lucadev.trampoline.security.logging.ActingUpon;
import com.lucadev.trampoline.security.logging.LogUserActivity;
import com.lucadev.trampoline.security.logging.UserActivity;
import com.lucadev.trampoline.security.logging.UserActivityInvocationContext;
import com.lucadev.trampoline.security.logging.handler.UserActivityHandler;
import com.lucadev.trampoline.service.time.TimeProvider;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.MapAccessor;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring AoP aspect to handle method invocations that have the {@link LogUserActivity}
 * annotation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Aspect
@Slf4j
@Order(20)
public class UserActivityLoggingAspect {

	private final UserActivityHandler userActivityHandler;

	private final TimeProvider timeProvider;

	private final SpelExpressionParser expressionParser;

	private final StandardEvaluationContext spelEvaluationContext;

	private final Map<String, Expression> spelDescriptions;

	public UserActivityLoggingAspect(UserActivityHandler userActivityHandler,
			TimeProvider timeProvider) {
		this.userActivityHandler = userActivityHandler;
		this.timeProvider = timeProvider;
		this.expressionParser = new SpelExpressionParser();
		this.spelEvaluationContext = new StandardEvaluationContext();
		this.spelEvaluationContext.addPropertyAccessor(new MapAccessor());
		this.spelDescriptions = new HashMap<>();
	}

	@Pointcut("@annotation(com.lucadev.trampoline.security.logging.LogUserActivity)")
	public void logUserActivityDefinition() {
	}

	@Around("logUserActivityDefinition()")
	public Object logUserActivity(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		LogUserActivity logUserActivity = method
				.getDeclaredAnnotation(LogUserActivity.class);
		UserDetails principal = getCurrentUser();
		Object actedUpon = null;
		Map<String, Object> argumentMap = new HashMap<>();

		for (int i = 0; i < method.getParameters().length; i++) {
			Parameter param = method.getParameters()[i];
			if (param.isAnnotationPresent(ActingUpon.class)) {
				actedUpon = joinPoint.getArgs()[i];
			}
			if (param.getName().startsWith("arg")) {
				log.warn(
						"Parameter name starts with \"arg\" prefix. Please make sure you compiled with the '-parameters' options.");
			}
			argumentMap.put(param.getName(), joinPoint.getArgs()[i]);
		}

		ActivityMethodInvocationResult result = runWrappedMethod(joinPoint);

		String description = logUserActivity.value();
		// Description was not filled in
		if ("".equals(description)) {
			description = method.getDeclaringClass().getSimpleName() + "#"
					+ method.getName();
		}

		if (logUserActivity.spel()) {
			description = evaluateSpelDescription(description, argumentMap);
		}

		UserActivity userActivity = new UserActivity(result.getInvocationDetails(),
				principal, description, actedUpon);

		try {
			this.userActivityHandler.handleUserActivity(userActivity);
		}
		finally {
			if (result.getThrowable() != null) {
				throw result.getThrowable();
			}
			else {
				return result.getResult();
			}
		}

	}

	/**
	 * Evaluates description defined in {@link LogUserActivity}. Uses a hashmap to prevent
	 * having to re-parse expressions.
	 * @param description the SPeL expression.
	 * @param methodArguments the arguments passed to the method we're intercepting.
	 * @return the evaluated expression.
	 */
	private String evaluateSpelDescription(String description,
			Map<String, Object> methodArguments) {
		Expression expression;
		if (this.spelDescriptions.containsKey(description)) {
			expression = this.spelDescriptions.get(description);
		}
		else {
			expression = this.expressionParser.parseExpression(description);
			this.spelDescriptions.put(description, expression);
		}

		try {
			return expression.getValue(this.spelEvaluationContext, methodArguments,
					String.class);
		}
		catch (EvaluationException evaluationException) {
			return description;
		}
	}

	/**
	 * Run the method we're intercepting and return a result we can understand.
	 * @param joinPoint the method
	 * @return a result containing our invocation results.
	 * @throws Throwable when we dont want to log methods that throw exceptions.
	 */
	private ActivityMethodInvocationResult runWrappedMethod(ProceedingJoinPoint joinPoint)
			throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		Class clazz = method.getDeclaringClass();
		LogUserActivity logUserActivity = method
				.getDeclaredAnnotation(LogUserActivity.class);

		long invocationStart = this.timeProvider.unix();
		long invocationEnd;
		Object returnValue = null;
		Throwable throwable = null;
		try {
			returnValue = joinPoint.proceed();
		}
		catch (Throwable ex) {
			// Still throw if we're skipping them
			if (!logUserActivity.logThrowables()) {
				throw ex;
			}
			throwable = ex;
		}
		finally {
			invocationEnd = this.timeProvider.unix();
		}
		boolean exceptionThrown = throwable != null;
		UserActivityInvocationContext invocationDetails = new UserActivityInvocationContext(
				clazz.getName(), method.getName(), exceptionThrown, invocationStart,
				invocationEnd);
		return new ActivityMethodInvocationResult(invocationDetails, returnValue,
				throwable);
	}

	/**
	 * Get user who invoked the activity.
	 * @return current authorized user from {@link SecurityContext}
	 */
	private UserDetails getCurrentUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext == null) {
			throw new NullPointerException(
					"Could not get current principal because SecurityContext is null.");
		}
		Authentication auth = securityContext.getAuthentication();
		if (auth == null) {
			throw new NullPointerException(
					"Could not get current principal because Authentication is null.");
		}
		Object principal = auth.getPrincipal();
		if (!(principal instanceof UserDetails)) {
			throw new NullPointerException(
					"Could not get current principal because it is not of type UserDetails");
		}
		return (UserDetails) principal;
	}

}
