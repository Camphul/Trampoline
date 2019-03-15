package com.lucadev.trampoline.security.abac;

import com.lucadev.trampoline.security.abac.policy.PolicyEnforcement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 20-5-18
 */
@AllArgsConstructor
public abstract class AbstractAbacPermissionEvaluator implements AbacPermissionEvaluator {

	@Getter
	protected final PolicyEnforcement policyEnforcement;

	@Override
	public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
		return false;
	}
}
