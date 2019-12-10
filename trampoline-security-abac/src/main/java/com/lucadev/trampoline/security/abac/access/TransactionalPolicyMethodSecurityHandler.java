package com.lucadev.trampoline.security.abac.access;

import com.lucadev.trampoline.reflect.MethodReflectionUtils;
import com.lucadev.trampoline.security.abac.PolicyEnforcement;
import com.lucadev.trampoline.security.abac.access.annotation.PolicyResource;
import com.lucadev.trampoline.security.abac.access.annotation.PostPolicy;
import com.lucadev.trampoline.security.abac.access.annotation.PrePolicy;
import com.lucadev.trampoline.security.abac.access.method.SecuredMethodInvocationIntercept;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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

	private final PolicyEnforcement policyEnforcement;

	/**
	 * Get parameter value which is annotation with @PolicyResource or get null.
	 * @param method method to get the policy resource from.
	 * @param paramArguments parameter arguments map.
	 * @return parameter value or null
	 */
	private Object getPolicyResource(Method method,
			Map<Parameter, Object> paramArguments) {
		Parameter parameter = MethodReflectionUtils
				.findFirstParameterWithAnnotation(method, PolicyResource.class);
		if (parameter == null) {
			return null;
		}

		return paramArguments.get(parameter);
	}

	/**
	 * Checks permissions/policies before invocating a method.
	 * @param method the method about to be invoked.
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
		Map<Parameter, Object> env = MethodReflectionUtils
				.mapParametersToArguments(method, arguments);
		Object resource = getPolicyResource(method, env);
		this.policyEnforcement.check(resource, prePolicy.value(),
				MethodReflectionUtils.mapToParameterNameKeys(env));
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
		}
		catch (Throwable throwable) {
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
		Map<Parameter, Object> env = MethodReflectionUtils
				.mapParametersToArguments(method, arguments);
		Object policyResource = getPolicyResource(method, env);
		Map<String, Object> namedParamArgs = MethodReflectionUtils
				.mapToParameterNameKeys(env);

		// If @PolicyResource is applied use that instead of return value.
		if (policyResource != null) {
			this.policyEnforcement.check(policyResource, postPolicy.value(),
					namedParamArgs);
		}
		else {
			this.policyEnforcement.check(returnValue, postPolicy.value(), namedParamArgs);
		}
		return returnValue;
	}

}
