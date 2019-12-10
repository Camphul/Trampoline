package com.lucadev.trampoline.security.abac.access.method;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * AspectJ implementation for method invocation wrapper.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @see SecuredMethodInvocationIntercept
 * @since 10/18/19
 */
@RequiredArgsConstructor
public class AspectJSecuredMethodInvocationIntercept
		implements SecuredMethodInvocationIntercept {

	@Getter
	private final ProceedingJoinPoint proceedingJoinPoint;

	@Override
	public Object proceed() throws Throwable {
		return this.proceedingJoinPoint.proceed();
	}

	@Override
	public Object proceed(Object[] arguments) throws Throwable {
		return this.proceedingJoinPoint.proceed(arguments);
	}

	@Override
	public Method getMethod() {
		return ((MethodSignature) this.proceedingJoinPoint.getSignature()).getMethod();
	}

	@Override
	public Object[] getArguments() {
		return this.proceedingJoinPoint.getArgs();
	}

}
