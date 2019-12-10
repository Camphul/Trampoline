package com.lucadev.trampoline.security.logging.aop;

import com.lucadev.trampoline.security.logging.UserActivityInvocationContext;
import lombok.Data;

/**
 * Wrapper for handling method invocation for logging.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/8/19
 */
@Data
public final class ActivityMethodInvocationResult {

	private final UserActivityInvocationContext invocationDetails;

	private final Object result;

	private final Throwable throwable;

}
