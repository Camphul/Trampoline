package com.lucadev.trampoline.security.abac.decision.voter;

import com.lucadev.trampoline.security.abac.spel.context.SecurityAccessContext;
import com.lucadev.trampoline.security.abac.persistence.entity.PolicyRule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationException;
import org.springframework.security.core.Authentication;


/**
 * Voter for a single policy rule.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 5/7/19
 */
@Getter
@RequiredArgsConstructor
public class PolicyRuleVoter extends AbstractPolicyVoter {

	private static final Logger LOGGER = LoggerFactory.getLogger(PolicyRuleVoter.class);
	private final PolicyRule policyRule;

	@Override
	public int vote(Authentication authentication, Object resource, SecurityAccessContext accessContext) {
		try {
			//If passes we grant
			if (policyRule.getCondition().getValue(accessContext, Boolean.class)) {
				return ACCESS_GRANTED;
			} else {
				return ACCESS_DENIED;
			}
		} catch (EvaluationException ex) {
			LOGGER.error("Failed to evaluate expression.", ex);
			return ACCESS_DENIED;
		}
	}
}
