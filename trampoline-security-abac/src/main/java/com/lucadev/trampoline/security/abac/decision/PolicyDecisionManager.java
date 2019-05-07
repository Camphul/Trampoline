package com.lucadev.trampoline.security.abac.decision;

import com.lucadev.trampoline.security.abac.PolicyContainer;
import com.lucadev.trampoline.security.abac.decision.voter.PolicyVoterAttribute;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.core.Authentication;

/**
 * PDP component for the ABAC system. Decides outcome.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 5/7/19
 */
public interface PolicyDecisionManager extends AccessDecisionManager {

	/**
	 * Get the policy container.
	 * @return container with all policies.
	 */
	PolicyContainer getPolicyContainer();

	/**
	 * Decide if we want to vote.
	 * @param authentication
	 * @param object
	 * @param voterAttribute
	 */
	void decide(Authentication authentication, Object object, PolicyVoterAttribute voterAttribute);
}
