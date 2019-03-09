package com.lucadev.trampoline.security.logging.activity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Context in which user activity took place.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Getter
@AllArgsConstructor
@ToString
public class UserActivityInvocationContext {

	private final long executionStart;
	private final long executionFinish;
	private final String className;
	private final String methodName;
	private final Object[] arguments;
	private final Object returnValue;
	private final boolean exceptionThrown;

}
