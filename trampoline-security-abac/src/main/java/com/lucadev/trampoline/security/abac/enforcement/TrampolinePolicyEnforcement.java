package com.lucadev.trampoline.security.abac.enforcement;

import com.lucadev.trampoline.security.abac.decision.PolicyDecisionManager;
import com.lucadev.trampoline.security.abac.decision.voter.PolicyVoterAttribute;
import com.lucadev.trampoline.security.abac.spel.context.SecurityAccessContext;
import com.lucadev.trampoline.security.abac.spel.context.SecurityAccessContextFactory;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 20-5-18
 */
@AllArgsConstructor
public class TrampolinePolicyEnforcement implements PolicyEnforcement {

	private static final Logger logger = LoggerFactory.getLogger(TrampolinePolicyEnforcement.class);
	private final SecurityAccessContextFactory securityAccessContextFactory;
	private final PolicyDecisionManager policyDecisionManager;

	/**
	 * Check if we have access to the given resource.
	 *
	 * @param subject     the identity trying to access the resource.
	 * @param resource    the resource which is being accessed
	 * @param action      the action that is taken against the resource
	 * @param environment the current context in which the action is taking place
	 * @return the result of the policy check, true means we are allowed to access and false should deny access.
	 */
	@Override
	public void check(Authentication subject, Object resource, String action, Object environment) {
		//Wrap the context
		SecurityAccessContext ctx = securityAccessContextFactory.create(subject, resource, action, environment);
		PolicyVoterAttribute voterAttribute = new PolicyVoterAttribute(ctx);
		//Filter the rules according to context.
		policyDecisionManager.decide((Authentication)subject, resource, voterAttribute);
		//finally, check if any of the rules are satisfied, otherwise return false.
	}

	/**
	 * Check if we have access to the given resource.
	 * In this method the Spring {@link org.springframework.security.core.context.SecurityContext} will be used.
	 *
	 * @param resource   the resource which is being accessed
	 * @param permission the permission required by the action against the resource.
	 */
	@Override
	public void check(Object resource, String permission) {
		Authentication auth = authenticationContext().orElseThrow(() -> new AccessDeniedException("Not authenticated."));

		check(auth, resource, permission, environmentContext());
	}

	/**
	 * Get the environment through the use of Spring context.
	 *
	 * @return environment built from Spring context.
	 */
	private Map<String, Object> environmentContext() {
		return new HashMap<>();
	}

	/**
	 * Get current authentication
	 *
	 * @return a {@link Authentication} from the security context.
	 */
	private Optional<Authentication> authenticationContext() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return Optional.empty();
		}
		return Optional.of(auth);
	}
}
