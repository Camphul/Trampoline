package com.lucadev.trampoline.security.abac;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * Abstract implementation for {@link AbstractAbacPermissionEvaluator}.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 20-5-18
 */
@AllArgsConstructor
public abstract class AbstractAbacPermissionEvaluator implements AbacPermissionEvaluator {

	@Getter
	protected final PolicyEnforcement policyEnforcement;

	/**
	 * Alternative method for evaluating a permission where only the identifier of the
	 * target object is available, rather than the target instance itself. This method
	 * should not be invoked when using ABAC since we require an object instance.
	 * @param authentication represents the user in question. Should not be null.
	 * @param targetId the identifier for the object instance (usually a Long)
	 * @param targetType a String representing the target's type (usually a Java
	 * classname). Not null.
	 * @param permission a representation of the permission object as supplied by the
	 * expression system. Not null.
	 * @return true if the permission is granted, false otherwise
	 */
	public boolean hasPermission(Authentication authentication, Serializable targetId,
			String targetType, Object permission) {
		return false;
	}

}
