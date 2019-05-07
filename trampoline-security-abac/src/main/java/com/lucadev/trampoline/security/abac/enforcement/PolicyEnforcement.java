package com.lucadev.trampoline.security.abac.enforcement;

import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * PEP(Policy Enforcement Point) for ABAC.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 20-5-18
 */
public interface PolicyEnforcement {

	/**
	 * Check if we have access to the given resource.
	 *
	 * @param subject     the identity trying to access the resource.
	 * @param resource    the resource which is being accessed
	 * @param action      the action that is taken against the resource
	 * @param environment the current context in which the action is taking place
	 * @return the result of the policy check, true means we are allowed to access and false should deny access.
	 */
	void check(Authentication subject, Object resource, String action, Object environment);

	/**
	 * Check if we have access to the given resource.
	 * In this method the Spring {@link org.springframework.security.core.context.SecurityContext} will be used.
	 *
	 * @param resource   the resource which is being accessed
	 * @param permission the permission required by the action against the resource.
	 */
	void check(Object resource, String permission);

}
