package com.lucadev.trampoline.security.abac.policy;

import com.lucadev.trampoline.data.repository.TrampolineRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 22-5-18
 */
@Repository
public interface PolicyRuleRepository extends TrampolineRepository<PolicyRule> {

    /**
     * Find one policy rule
     *
     * @param name
     * @return
     */
    PolicyRule findOneByName(String name);

    /**
     * Count policy by name
     *
     * @param name
     * @return
     */
    long countByName(String name);
}
