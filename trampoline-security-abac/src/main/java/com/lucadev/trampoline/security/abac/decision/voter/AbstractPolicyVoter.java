package com.lucadev.trampoline.security.abac.decision.voter;

import org.springframework.expression.EvaluationException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import java.util.Collection;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 5/7/19
 */
public abstract class AbstractPolicyVoter implements PolicyVoter<Object> {

	/**
	 * Support when target eval is true.
	 * @param attribute
	 * @return
	 */
	@Override
	public boolean supports(ConfigAttribute attribute) {
		if(!(attribute instanceof PolicyVoterAttribute)) {
			return false;
		}

		PolicyVoterAttribute voterAttribute = (PolicyVoterAttribute)attribute;
		try {
			return getPolicyRule().getTarget().getValue(voterAttribute.getSecurityAccessContext(), Boolean.class);
		} catch (EvaluationException ex) {
			//No support
			return false;
		}
	}

	/**
	 * Support all classes.
 	 * @param clazz
	 * @return
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	/**
	 * Votes
	 * @param authentication
	 * @param object
	 * @param attributes
	 * @return
	 */
	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		if(attributes.size() != 1) {
			//Require exactly one attribute.
			return ACCESS_DENIED;
		}
		ConfigAttribute attribute = attributes.toArray(new ConfigAttribute[attributes.size()])[0];
		if(attribute == null) {
			return ACCESS_DENIED;
		}

		if(!(attribute instanceof PolicyVoterAttribute)) {
			return ACCESS_DENIED;
		}

		if(!supports(attribute)) {
			return ACCESS_ABSTAIN;
		}

		PolicyVoterAttribute voterAttribute = (PolicyVoterAttribute)attribute;
		return vote(authentication, object, voterAttribute.getSecurityAccessContext());
	}
}
