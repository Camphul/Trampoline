package com.lucadev.trampoline.security.logging.aop;

import com.lucadev.trampoline.security.logging.UserActivityInvocationDetails;
import lombok.Data;

/**
 * Wrapper for handling method invocation for logging.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 5/8/19
 */
@Data
public class ActivityMethodInvocationResult {

	private final UserActivityInvocationDetails invocationDetails;

	private final Object result;

	private final Throwable throwable;

}
