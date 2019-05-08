package com.lucadev.trampoline.security.abac;

import org.springframework.security.access.PermissionEvaluator;

/**
 * Abac-specific {@link PermissionEvaluator} extension.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 20-5-18
 */
public interface AbacPermissionEvaluator extends PermissionEvaluator {

	/**
	 * Get the policy enforcement being used by the evaluator.
	 *
	 * @return a {@link PolicyEnforcement}
	 */
	PolicyEnforcement getPolicyEnforcement();
}
