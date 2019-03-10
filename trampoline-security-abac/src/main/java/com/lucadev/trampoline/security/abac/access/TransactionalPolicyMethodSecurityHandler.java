package com.lucadev.trampoline.security.abac.access;

import com.lucadev.trampoline.security.abac.access.prepost.PostPolicy;
import com.lucadev.trampoline.security.abac.access.prepost.PrePolicy;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/10/19
 */
@Component
@AllArgsConstructor
public class TransactionalPolicyMethodSecurityHandler implements PolicyMethodSecurityHandler {

	private static final Logger LOG = LoggerFactory.getLogger(TransactionalPolicyMethodSecurityHandler.class);
	private final PermissionEvaluator permissionEvaluator;

	@Override
	public void handlePrePolicy(JoinPoint joinPoint) {
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		PrePolicy prePolicy = method.getAnnotation(PrePolicy.class);

		LOG.debug("PrePolicy checking {}#{}", method.getDeclaringClass().getName(), method.getName());
		boolean permission = permissionEvaluator.hasPermission(authentication, null, prePolicy.value());
		if (!permission) {
			throw new AccessDeniedException("Forbidden");
		}
	}

	@Transactional
	@Override
	public Object handlePostPolicy(ProceedingJoinPoint joinPoint) throws Throwable {
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		PostPolicy postPolicy = method.getAnnotation(PostPolicy.class);

		Object returnValue = null;
		try {
			returnValue = joinPoint.proceed();
		} catch (Throwable throwable) {
			LOG.error("PostPolicy proxy received throwable.", throwable);
			throw throwable;
		}
		boolean permission = permissionEvaluator.hasPermission(authentication, returnValue, postPolicy.value());
		if (permission) {
			return returnValue;
		}
		throw new AccessDeniedException("Forbidden");
	}
}
