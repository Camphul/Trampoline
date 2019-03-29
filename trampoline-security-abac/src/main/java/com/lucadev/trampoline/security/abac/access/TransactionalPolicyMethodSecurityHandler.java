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
 * Transactional implementation of {@link PolicyMethodSecurityHandler} used to rollback transactions when unauthorized.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
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

		boolean permission = permissionEvaluator.hasPermission(authentication, null, prePolicy.value());
		if (!permission) {
			throw new AccessDeniedException("Forbidden");
		}
	}

	@Transactional
	@Override
	public Object handlePostPolicy(ProceedingJoinPoint joinPoint) throws Throwable {
		//Obtain method details
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		PostPolicy postPolicy = method.getAnnotation(PostPolicy.class);

		Object returnValue = null;
		//Attempt to invocate method
		try {
			returnValue = joinPoint.proceed();
		} catch (Throwable throwable) {
			LOG.error("PostPolicy proxy received throwable.", throwable);
			throw throwable;
		}
		//Check if invocated method has permissions
		boolean permission = permissionEvaluator.hasPermission(authentication, returnValue, postPolicy.value());
		if (permission) {
			return returnValue;
		}
		//Throw 401 when permission check returned false.
		throw new AccessDeniedException("Forbidden");
	}
}
