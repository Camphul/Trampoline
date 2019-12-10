package com.lucadev.trampoline.security.abac.impl;

import com.lucadev.trampoline.security.abac.PolicyContainer;
import com.lucadev.trampoline.security.abac.persistence.entity.PolicyRule;
import com.lucadev.trampoline.security.abac.persistence.repository.PolicyRuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * A {@link PolicyContainer} which persists {@link PolicyRule} through JPA.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 22-5-18
 */
@Slf4j
@RequiredArgsConstructor
public class JpaPolicyContainer implements PolicyContainer {

	/**
	 * Name of the cache region used to store policies.
	 */
	public static final String POLICY_RULE_CACHE_REGION = "trampoline_policy_rule_cache";

	private final PolicyRuleRepository policyRuleRepository;

	/**
	 * Import parent policy rules into the JPA datasource.
	 * @param repository the repository to use.
	 * @param parent the parent policy definition, null is accepted.
	 */
	public JpaPolicyContainer(PolicyRuleRepository repository, PolicyContainer parent) {
		this(repository);
		if (parent != null) {
			importPolicyRules(parent);
		}
	}

	private void importPolicyRules(PolicyContainer parent) {
		log.debug("Importing policy rules from parent container.");
		List<PolicyRule> policyRules = parent.findAllPolicyRules();

		policyRules.stream()
				// Only add when none exist with the same name
				.filter(r -> !hasPolicyRule(r.getName())).forEach(this::addPolicyRule);
	}

	@Cacheable(POLICY_RULE_CACHE_REGION)
	@Override
	public List<PolicyRule> findAllPolicyRules() {
		return this.policyRuleRepository.findAll();
	}

	@Override
	@Cacheable(POLICY_RULE_CACHE_REGION)
	public boolean hasPolicyRule(String name) {
		return this.policyRuleRepository.countByName(name) > 0;
	}

	@Override
	@CacheEvict(POLICY_RULE_CACHE_REGION)
	public PolicyRule addPolicyRule(PolicyRule policyRule) {
		log.debug("Saving policy rule {}", policyRule.getName());
		return this.policyRuleRepository.save(policyRule);
	}

	@Override
	@CacheEvict(POLICY_RULE_CACHE_REGION)
	public PolicyRule updatePolicyRule(PolicyRule policyRule) {
		return addPolicyRule(policyRule);
	}

}
