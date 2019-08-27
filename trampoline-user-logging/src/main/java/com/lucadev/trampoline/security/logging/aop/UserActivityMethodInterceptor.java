package com.lucadev.trampoline.security.logging.aop;

import com.lucadev.trampoline.security.logging.ActingUpon;
import com.lucadev.trampoline.security.logging.LogUserActivity;
import com.lucadev.trampoline.security.logging.UserActivity;
import com.lucadev.trampoline.security.logging.UserActivityInvocationContext;
import com.lucadev.trampoline.security.logging.handler.UserActivityHandler;
import com.lucadev.trampoline.service.time.TimeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
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
 * Method interceptor used to intercept user activity.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/3/19
 */
@Slf4j
@RequiredArgsConstructor
public class UserActivityMethodInterceptor implements MethodInterceptor {

	private final UserActivityHandler userActivityHandler;

	private final TimeProvider timeProvider;

	private SpelExpressionParser expressionParser = new SpelExpressionParser();

	private Map<String, Expression> spelDescriptions = new HashMap<>();

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		LogUserActivity logUserActivity = method
				.getDeclaredAnnotation(LogUserActivity.class);
		UserDetails principal = getUserDetails();
		Object actedUpon = null;
		Map<String, Object> argumentMap = new HashMap<>();

		for (int i = 0; i < method.getParameters().length; i++) {
			Parameter param = method.getParameters()[i];
			if (param.isAnnotationPresent(ActingUpon.class)) {
				actedUpon = invocation.getArguments()[i];
			}
			if (param.getName().startsWith("arg")) {
				log.warn(
						"Parameter name starts with \"arg\" prefix. Please make sure you compiled with the '-parameters' options.");
			}
			argumentMap.put(param.getName(), invocation.getArguments()[i]);
		}

		ActivityMethodInvocationResult result = proceed(invocation);

		String description = logUserActivity.value();
		// Description was not filled in
		if ("".equals(description)) {
			description = method.getDeclaringClass().getSimpleName() + "#"
					+ method.getName();
		}

		if (logUserActivity.spel()) {
			description = evaluateDescription(description, argumentMap);
		}

		UserActivity userActivity = new UserActivity(result.getInvocationDetails(),
				principal, description, actedUpon);

		try {
			this.userActivityHandler.handleUserActivity(userActivity);
		} finally {
			if (result.getThrowable() != null) {
				throw result.getThrowable();
			} else {
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
	private String evaluateDescription(String description,
									   Map<String, Object> methodArguments) {
		EvaluationContext spelEvaluationContext = new StandardEvaluationContext();
		Expression expression;
		if (this.spelDescriptions.containsKey(description)) {
			expression = this.spelDescriptions.get(description);
		} else {
			expression = this.expressionParser.parseExpression(description);
			this.spelDescriptions.put(description, expression);
		}

		try {
			return expression.getValue(spelEvaluationContext, methodArguments,
					String.class);
		} catch (EvaluationException evaluationException) {
			return description;
		}
	}

	/**
	 * Run the method we're intercepting and return a result we can understand.
	 * @param invocation the method invocation
	 * @return a result containing our invocation results.
	 * @throws Throwable when we dont want to log methods that throw exceptions.
	 */
	private ActivityMethodInvocationResult proceed(MethodInvocation invocation)
			throws Throwable {
		Method method = invocation.getMethod();
		Class clazz = method.getDeclaringClass();
		LogUserActivity logUserActivity = method
				.getDeclaredAnnotation(LogUserActivity.class);

		long invocationStart = this.timeProvider.unix();
		long invocationEnd;
		Object returnValue = null;
		Throwable throwable = null;
		try {
			returnValue = invocation.proceed();
		} catch (Throwable ex) {
			// Still throw if we're skipping them
			if (!logUserActivity.logThrowables()) {
				throw ex;
			}
			throwable = ex;
		} finally {
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
	private UserDetails getUserDetails() {
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
