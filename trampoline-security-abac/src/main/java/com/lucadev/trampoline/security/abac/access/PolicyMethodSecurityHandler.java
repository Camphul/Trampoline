package com.lucadev.trampoline.security.abac.access;

import com.lucadev.trampoline.security.abac.access.method.SecuredMethodInvocationIntercept;

import java.lang.reflect.Method;

/**
 * Interface for handling method security using pre/post policy.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/10/19
 */
public interface PolicyMethodSecurityHandler {

	/**
	 * Get triggered before method body executes.
	 * @param method the method about to be invoked.
	 * @param arguments the arguments used in the method invocation.
	 */
	void handlePrePolicy(Method method, Object[] arguments);

	/**
	 * Gets triggered around the method body and acts accordingly.
	 * @param securedMethodInvocationIntercept wrapper to abstract away method invocation
	 * details.
	 * @throws Throwable when the invoked method throws an exception.
	 */
	Object handlePostPolicy(
			SecuredMethodInvocationIntercept securedMethodInvocationIntercept)
			throws Throwable;

}
