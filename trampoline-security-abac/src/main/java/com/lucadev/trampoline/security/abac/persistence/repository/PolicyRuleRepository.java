package com.lucadev.trampoline.security.abac.persistence.repository;

import com.lucadev.trampoline.data.repository.TrampolineRepository;
import com.lucadev.trampoline.security.abac.persistence.entity.PolicyRule;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link PolicyRule}.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 22-5-18
 */
@Repository
public interface PolicyRuleRepository extends TrampolineRepository<PolicyRule> {

	/**
	 * Find one policy rule.
	 * @param name policy rule name.
	 * @return resolved policy.
	 */
	PolicyRule findOneByName(String name);

	/**
	 * Count policy by name.
	 * @param name policy rule name.
	 * @return amount of rules by the name.
	 */
	long countByName(String name);

}
