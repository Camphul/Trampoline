package com.lucadev.trampoline.security.logging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * POJO with invocation details regarding a user activity.
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/10/19
 */
@Getter
@ToString
@AllArgsConstructor
public class UserActivityInvocationDetails {

	private final String className;
	private final String methodName;
	private final boolean exceptionThrown;
	private final long invocationStart;
	private final long invocationEnd;

}
