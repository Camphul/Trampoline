package com.lucadev.trampoline.security.abac.access;

import com.lucadev.trampoline.security.abac.PolicyEnforcement;
import com.lucadev.trampoline.security.abac.access.annotation.PolicyResource;
import com.lucadev.trampoline.security.abac.access.annotation.PostPolicy;
import com.lucadev.trampoline.security.abac.access.annotation.PrePolicy;
import com.lucadev.trampoline.security.abac.access.method.SecuredMethodInvocationIntercept;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * Transactional implementation of {@link PolicyMethodSecurityHandler} used to rollback
 * transactions when unauthorized.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/10/19
 */
@Slf4j
@Component
@AllArgsConstructor
public class TransactionalPolicyMethodSecurityHandler
		implements PolicyMethodSecurityHandler {

	private static final Logger LOG = LoggerFactory
			.getLogger(TransactionalPolicyMethodSecurityHandler.class);

	private final PolicyEnforcement policyEnforcement;

	/**
	 * Get parameter value which is annotation with @PolicyResource or get null.
	 * @param method method to get the policy resource from.
	 * @param arguments arguments used in the invocation.
	 * @return parameter value or null
	 */
	private Object getPolicyResource(Method method, Object[] arguments) {
		for (int paramCounter = 0; paramCounter < method
				.getParameters().length; paramCounter++) {
			Parameter parameter = method.getParameters()[paramCounter];
			PolicyResource policyResource = AnnotationUtils.findAnnotation(parameter,
					PolicyResource.class);
			if (policyResource != null) {
				// policy resource found
				return arguments[paramCounter];
			}
		}
		return null;
	}

	/**
	 * Checks permissions/policies before invocating a method.
	 *
	 * @param method    the method about to be invoked.
	 * @param arguments the arguments used in the method invocation.
	 */
	@Override
	public void handlePrePolicy(Method method, Object[] arguments) {
		PrePolicy prePolicy = AnnotationUtils.findAnnotation(method, PrePolicy.class);
		if (prePolicy == null) {
			log.error("@PrePolicy not found on proxied method {}#{}",
					method.getDeclaringClass().getName(), method.getName());
			throw new NullPointerException(
					"Intercepted method requires @PrePolicy annotation.");
		}
		// executed.
		Object resource = getPolicyResource(method, arguments);
		Map<String, Object> env = getParameterArgumentMap(method, arguments);
		this.policyEnforcement.check(resource, prePolicy.value(), env);
	}

	/**
	 * Creates a map with param name and value.
	 *
	 * @param method method being invoked with those arguments. * @param args parameter
	 *               values.
	 * @return map with PARAM NAME-PARAM VALUE structure.
	 */
	private Map<String, Object> getParameterArgumentMap(Method method, Object[] args) {
		Map<String, Object> environment = new HashMap<>();
		for (int paramCount = 0; paramCount < method
				.getParameters().length; paramCount++) {
			Parameter parameter = method.getParameters()[paramCount];
			environment.put(parameter.getName(), args[paramCount]);
		}
		return environment;
	}

	/**
	 * Handles checking policy after method invocation. Is transactional so that any
	 * transactions that happended unauthorized can be rolled back.
	 * @param securedMethodInvocationIntercept wrapper to abstract away method invocation
	 * details.
	 * @return method return value.
	 * @throws Throwable when the invocation of the secured method throws an exception.
	 */
	@Transactional
	@Override
	public Object handlePostPolicy(
			SecuredMethodInvocationIntercept securedMethodInvocationIntercept)
			throws Throwable {
		// Obtain method details
		Method method = securedMethodInvocationIntercept.getMethod();
		Object[] arguments = securedMethodInvocationIntercept.getArguments();

		Object returnValue = null;

		try {
			returnValue = securedMethodInvocationIntercept.proceed();
		} catch (Throwable throwable) {
			log.error("Proxied method threw exception.", throwable);
			throw throwable;
		}

		PostPolicy postPolicy = AnnotationUtils.findAnnotation(method, PostPolicy.class);
		if (postPolicy == null) {
			log.error("@PostPolicy not found on proxied method {}#{}",
					method.getDeclaringClass().getName(), method.getName());
			throw new NullPointerException(
					"Intercepted method requires @PostPolicy annotation.");
		}

		// executed.
		Object policyResource = getPolicyResource(method, arguments);
		Map<String, Object> env = getParameterArgumentMap(method, arguments);

		// If @PolicyResource is applied use that instead of return value.
		if (policyResource != null) {
			this.policyEnforcement.check(policyResource, postPolicy.value(), env);
		} else {
			this.policyEnforcement.check(returnValue, postPolicy.value(), env);
		}
		return returnValue;
	}

}
