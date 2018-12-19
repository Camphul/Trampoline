package com.lucadev.trampoline.security.abac.policy.impl;

import com.lucadev.trampoline.security.abac.policy.PolicyDefinition;
import com.lucadev.trampoline.security.abac.policy.PolicyRule;
import com.lucadev.trampoline.security.abac.policy.PolicyRuleRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * A {@link PolicyDefinition} which persists {@link PolicyRule} through JPA.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 22-5-18
 */
@AllArgsConstructor
public class JpaPolicyDefinition implements PolicyDefinition {

    public static final String POLICY_RULE_CACHE_REGION = "trampoline_policy_rule_cache";
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaPolicyDefinition.class);
    private final PolicyRuleRepository policyRuleRepository;

    /**
     * Import parent policy rules into the JPA datasource.
     *
     * @param repository the repository to use.
     * @param parent     the parent policy definition, null is accepted.
     */
    public JpaPolicyDefinition(PolicyRuleRepository repository, PolicyDefinition parent) {
        this(repository);
        if (parent != null) {
            importPolicyRules(parent);
        }
    }

    private void importPolicyRules(PolicyDefinition parent) {
        LOGGER.info("Importing policy rules...");
        parent.findAllPolicyRules().stream()
                //Only add when none exist with the same name
                .filter(r -> !hasPolicyRule(r.getName()))
                .forEach(this::addPolicyRule);
    }

    @Cacheable(POLICY_RULE_CACHE_REGION)
    @Override
    public List<PolicyRule> findAllPolicyRules() {
        return policyRuleRepository.findAll();
    }

    @Override
    @Cacheable(POLICY_RULE_CACHE_REGION)
    public boolean hasPolicyRule(String name) {
        return policyRuleRepository.countByName(name) > 0;
    }

    @Override
    @CacheEvict(POLICY_RULE_CACHE_REGION)
    public PolicyRule addPolicyRule(PolicyRule policyRule) {
        LOGGER.debug("Saving policy rule {}", policyRule.getName());
        return policyRuleRepository.save(policyRule);
    }

    @Override
    @CacheEvict(POLICY_RULE_CACHE_REGION)
    public PolicyRule updatePolicyRule(PolicyRule policyRule) {
        return addPolicyRule(policyRule);
    }
}
