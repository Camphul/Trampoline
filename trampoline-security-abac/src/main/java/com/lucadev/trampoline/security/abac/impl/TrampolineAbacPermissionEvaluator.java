package com.lucadev.trampoline.security.abac.impl;

import com.lucadev.trampoline.security.abac.AbstractAbacPermissionEvaluator;
import com.lucadev.trampoline.security.abac.PolicyEnforcement;
import com.lucadev.trampoline.security.abac.decorator.PolicyEnvironmentDecorator;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Default {@link com.lucadev.trampoline.security.abac.AbacPermissionEvaluator}
 * implementation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 20-5-18
 */
public class TrampolineAbacPermissionEvaluator extends AbstractAbacPermissionEvaluator {

	private final List<PolicyEnvironmentDecorator> environmentDecorators;

	public TrampolineAbacPermissionEvaluator(PolicyEnforcement policyEnforcement,
			List<PolicyEnvironmentDecorator> environmentDecorators) {
		super(policyEnforcement);
		this.environmentDecorators = environmentDecorators;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject,
			Object permission) {
		if (authentication == null) {
			throw new AccessDeniedException("Not authenticated");
		}
		Object user = authentication.getPrincipal();
		Map<String, Object> environment = new HashMap<>();

		this.environmentDecorators.forEach(decorator -> decorator.decorate(environment));

		boolean allowed = this.policyEnforcement.check(user, targetDomainObject,
				permission, environment);

		if (!allowed) {
			throw new AccessDeniedException("Principal is denied access to resource.");
		}

		return true;
	}

}
