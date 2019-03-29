package com.lucadev.trampoline.security.logging.aop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Context in which user activity took place.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Getter
@AllArgsConstructor
@ToString
public class InterceptedUserActivityInvocationContext {

	private final long invocationStart;
	private final long invocationEnd;
	private final String className;
	private final String methodName;
	private final Object[] arguments;
	private final Object returnValue;
	private final boolean exceptionThrown;

}
