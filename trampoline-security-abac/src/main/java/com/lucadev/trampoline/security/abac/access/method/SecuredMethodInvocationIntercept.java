package com.lucadev.trampoline.security.abac.access.method;

import java.lang.reflect.Method;

/**
 * Wrapper for aspects used to handle method invocation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 10/18/19
 */
public interface SecuredMethodInvocationIntercept {

	/**
	 * Invokes the method.
	 * @return invocation return value.
	 * @throws Throwable exception thrown by method that was invoked.
	 */
	Object proceed() throws Throwable;

	/**
	 * Invokes the method.
	 * @param arguments custom arguments to invoke the method with.
	 * @return invocation return value.
	 * @throws Throwable exception thrown by method that was invoked.
	 */
	Object proceed(Object[] arguments) throws Throwable;

	/**
	 * Get method that is being invoked.
	 * @return the method being invoked.
	 */
	Method getMethod();

	/**
	 * Get the arguments passed to the method.
	 * @return arguments used to invoke the method.
	 */
	Object[] getArguments();

}
