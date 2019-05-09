package com.lucadev.trampoline.security.abac.access;

import com.lucadev.trampoline.security.abac.PolicyEnforcement;
import com.lucadev.trampoline.security.abac.access.annotation.PolicyResource;
import com.lucadev.trampoline.security.abac.access.annotation.PostPolicy;
import com.lucadev.trampoline.security.abac.access.annotation.PrePolicy;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Component
@AllArgsConstructor
public class TransactionalPolicyMethodSecurityHandler
		implements PolicyMethodSecurityHandler {

	private static final Logger LOG = LoggerFactory
			.getLogger(TransactionalPolicyMethodSecurityHandler.class);

	private final PolicyEnforcement policyEnforcement;

	/**
	 * Get parameter value which is annotation with @PolicyResource or get null.
	 * @param joinPoint method invocation
	 * @return parameter value or null
	 */
	private Object getPolicyResource(JoinPoint joinPoint) {
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

		for (int paramCounter = 0; paramCounter < method
				.getParameters().length; paramCounter++) {
			Parameter parameter = method.getParameters()[paramCounter];
			PolicyResource policyResource = parameter.getAnnotation(PolicyResource.class);
			if (policyResource != null) {
				// policy resource found
				return joinPoint.getArgs()[paramCounter];
			}
		}
		return null;
	}

	@Override
	public void handlePrePolicy(JoinPoint joinPoint) {
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

		PrePolicy prePolicy = method.getAnnotation(PrePolicy.class);
		Object resource = getPolicyResource(joinPoint);
		Map<String, Object> env = createEnvironment(joinPoint.getArgs(), method);
		this.policyEnforcement.check(resource, prePolicy.value(), env);
	}

	private Map<String, Object> createEnvironment(Object[] args, Method method) {
		Map<String, Object> environment = new HashMap<>();
		for (int paramCount = 0; paramCount < method
				.getParameters().length; paramCount++) {
			Parameter parameter = method.getParameters()[paramCount];
			environment.put(parameter.getName(), args[paramCount]);
		}
		return environment;
	}

	@Transactional
	@Override
	public Object handlePostPolicy(ProceedingJoinPoint joinPoint) throws Throwable {
		// Obtain method details
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		PostPolicy postPolicy = method.getAnnotation(PostPolicy.class);

		Object policyResource = getPolicyResource(joinPoint);
		Object returnValue = null;
		// Attempt to invocate method
		try {
			returnValue = joinPoint.proceed();
		}
		catch (Throwable throwable) {
			LOG.error("PostPolicy proxy received throwable.", throwable);
			throw throwable;
		}
		Map<String, Object> env = createEnvironment(joinPoint.getArgs(), method);

		// If @PolicyResource is applied use that instead of return value.
		if (policyResource != null) {
			this.policyEnforcement.check(policyResource, postPolicy.value(), env);
		}
		else {
			this.policyEnforcement.check(returnValue, postPolicy.value(), env);
		}
		return returnValue;
	}

}
