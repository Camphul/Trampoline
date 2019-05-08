package com.lucadev.trampoline.security.abac.access;

import com.lucadev.trampoline.security.abac.access.annotation.PrePolicy;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Proxy aspect for {@link PrePolicy}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/10/19
 */
@Aspect
@Component
@Order(1)
@AllArgsConstructor
public class PolicyMethodSecurityAspect {

	private final PolicyMethodSecurityHandler policyMethodSecurityHandler;

	@Pointcut("execution(* *(..)) && @annotation(com.lucadev.trampoline.security.abac.access.annotation.PrePolicy)")
	public void prePolicyDefinition() {
	}

	@Pointcut("execution(* *(..)) && @annotation(com.lucadev.trampoline.security.abac.access.annotation.PostPolicy)")
	public void postPolicyDefinition() {
	}

	@Before("prePolicyDefinition()")
	public void handlePrePolicy(JoinPoint joinPoint) {
		policyMethodSecurityHandler.handlePrePolicy(joinPoint);
	}

	@Around("postPolicyDefinition()")
	public Object handlePostPolicy(ProceedingJoinPoint joinPoint) throws Throwable {
		return policyMethodSecurityHandler.handlePostPolicy(joinPoint);
	}

}
