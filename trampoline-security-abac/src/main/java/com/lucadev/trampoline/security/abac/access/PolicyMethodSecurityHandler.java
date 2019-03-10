package com.lucadev.trampoline.security.abac.access;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Interface for handling method security using pre/post policy.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/10/19
 */
public interface PolicyMethodSecurityHandler {

	/**
	 * Get triggered before method body executes.
	 *
	 * @param joinPoint
	 */
	void handlePrePolicy(JoinPoint joinPoint);

	/**
	 * Gets triggered around the method body and acts accordingly.
	 *
	 * @param joinPoint
	 * @return return value.
	 */
	Object handlePostPolicy(ProceedingJoinPoint joinPoint);
}
