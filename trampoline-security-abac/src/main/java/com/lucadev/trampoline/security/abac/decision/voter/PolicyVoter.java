package com.lucadev.trampoline.security.abac.decision.voter;

import com.lucadev.trampoline.security.abac.spel.context.SecurityAccessContext;
import com.lucadev.trampoline.security.abac.persistence.entity.PolicyRule;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.core.Authentication;

/**
 * PolicyVoters
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 5/7/19
 */
public interface PolicyVoter<S> extends AccessDecisionVoter<S> {

	/**
	 * Get represented rule.
	 * @return
	 */
	PolicyRule getPolicyRule();

	/**
	 * Vote for access
	 * @param authentication
	 * @param resource
	 * @param accessContext
	 * @return
	 */
	int vote(Authentication authentication, Object resource, SecurityAccessContext accessContext);
}
