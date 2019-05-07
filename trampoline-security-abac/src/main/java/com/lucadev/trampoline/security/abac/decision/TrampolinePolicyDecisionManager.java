package com.lucadev.trampoline.security.abac.decision;

import com.lucadev.trampoline.security.abac.PolicyContainer;
import com.lucadev.trampoline.security.abac.decision.voter.PolicyRuleVoter;
import com.lucadev.trampoline.security.abac.decision.voter.PolicyVoterAttribute;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 5/7/19
 */
public class TrampolinePolicyDecisionManager extends UnanimousBased implements PolicyDecisionManager {

	private final PolicyContainer policyContainer;

	public TrampolinePolicyDecisionManager(PolicyContainer policyContainer) {
		super(policyContainer.findAllPolicyRules().stream().map(PolicyRuleVoter::new).collect(Collectors.toList()));
		this.policyContainer = policyContainer;
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return attribute instanceof PolicyVoterAttribute;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public PolicyContainer getPolicyContainer() {
		return policyContainer;
	}

	@Override
	public void decide(Authentication authentication, Object object, PolicyVoterAttribute voterAttribute) {
		List<ConfigAttribute> attributes = new ArrayList<>();
		attributes.add(voterAttribute);
		decide(authentication, object, attributes);
	}
}
