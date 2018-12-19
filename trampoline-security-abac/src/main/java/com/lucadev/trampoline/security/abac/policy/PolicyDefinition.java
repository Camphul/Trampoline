package com.lucadev.trampoline.security.abac.policy;

import java.util.List;

/**
 * Interface that defines methods to load policy rules.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 20-5-18
 */
public interface PolicyDefinition {

    /**
     * Get all the defined policy rules
     *
     * @return a {@link List} of all defined {@link PolicyRule} objects.
     */
    List<PolicyRule> findAllPolicyRules();

    /**
     * Check if the policy rule exists.
     *
     * @param name the name attribute of the policy
     * @return if the policy exists.
     */
    boolean hasPolicyRule(String name);

    /**
     * Add a new policy rule
     *
     * @param policyRule the policy rule to add
     * @return the saved rule.
     */
    PolicyRule addPolicyRule(PolicyRule policyRule);

    /**
     * Update a policy rule.
     *
     * @param policyRule the policy role to update.
     * @return the updated rule.
     */
    PolicyRule updatePolicyRule(PolicyRule policyRule);

}

