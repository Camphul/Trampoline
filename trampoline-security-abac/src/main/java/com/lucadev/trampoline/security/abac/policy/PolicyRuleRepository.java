package com.lucadev.trampoline.security.abac.policy;

import com.lucadev.trampoline.data.repository.TrampolineRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link PolicyRule}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 22-5-18
 */
@Repository
public interface PolicyRuleRepository extends TrampolineRepository<PolicyRule> {

	/**
	 * Find one policy rule
	 *
	 * @param name policy rule name.
	 * @return resolved policy.
	 */
	PolicyRule findOneByName(String name);

	/**
	 * Count policy by name
	 *
	 * @param name policy rule name.
	 * @return amount of rules by the name.
	 */
	long countByName(String name);
}
