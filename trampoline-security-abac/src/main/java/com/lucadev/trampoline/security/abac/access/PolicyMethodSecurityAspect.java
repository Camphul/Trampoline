package com.lucadev.trampoline.security.abac.access;

import com.lucadev.trampoline.security.abac.access.annotation.PostPolicy;
import com.lucadev.trampoline.security.abac.access.annotation.PrePolicy;
import com.lucadev.trampoline.security.abac.access.method.AspectJSecuredMethodInvocationIntercept;
import com.lucadev.trampoline.security.abac.access.method.SecuredMethodInvocationIntercept;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * Proxy aspect for {@link PrePolicy} and {@link PostPolicy}.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/10/19
 */
@Aspect
@Order(1)
@Slf4j
@RequiredArgsConstructor
public class PolicyMethodSecurityAspect {

	/**
	 * Handler bean to handle {@link PrePolicy} and {@link PostPolicy} method intercepts.
	 */
	private final PolicyMethodSecurityHandler policyMethodSecurityHandler;

	/**
	 * Pointcut defining to intercept the execution of methods annotated with
	 * {@link PrePolicy}
	 */
	@Pointcut("execution(* *(..)) && @annotation(com.lucadev.trampoline.security.abac.access.annotation.PrePolicy)")
	public void prePolicyDefinition() {
	}

	/**
	 * Pointcut defining to intercept the execution of methods annotated with
	 * {@link PostPolicy}
	 */
	@Pointcut("execution(* *(..)) && @annotation(com.lucadev.trampoline.security.abac.access.annotation.PostPolicy)")
	public void postPolicyDefinition() {
	}

	/**
	 * Invoked before the pointcut for {@link PrePolicy} is reached. The handler will
	 * decide if we want to continue execution of the pointcut.
	 * @param joinPoint AOP joinpoint.
	 */
	@Before("prePolicyDefinition()")
	public void handlePrePolicy(JoinPoint joinPoint) {
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		Object[] arguments = joinPoint.getArgs();
		this.policyMethodSecurityHandler.handlePrePolicy(method, arguments);
	}

	/**
	 * Invoked around the pointcut for {@link PostPolicy} annotated methods. The handler
	 * will decide if we want to continue execution of the pointcut.
	 * @param joinPoint AOP joinpoint.
	 */
	@Around("postPolicyDefinition()")
	public Object handlePostPolicy(ProceedingJoinPoint joinPoint) throws Throwable {
		SecuredMethodInvocationIntercept securedMethodInvocationIntercept = new AspectJSecuredMethodInvocationIntercept(
				joinPoint);
		return this.policyMethodSecurityHandler
				.handlePostPolicy(securedMethodInvocationIntercept);
	}

}
