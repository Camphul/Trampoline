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
    List<PolicyRule> getAllPolicyRules();

}
