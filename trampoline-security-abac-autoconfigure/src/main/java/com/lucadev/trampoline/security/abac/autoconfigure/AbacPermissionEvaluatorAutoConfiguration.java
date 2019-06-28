package com.lucadev.trampoline.security.abac.autoconfigure;

import com.lucadev.trampoline.security.abac.AbacPermissionEvaluator;
import com.lucadev.trampoline.security.abac.PolicyEnforcement;
import com.lucadev.trampoline.security.abac.impl.TrampolineAbacPermissionEvaluator;
import com.lucadev.trampoline.service.time.TimeProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfigure the {@link org.springframework.security.access.PermissionEvaluator} to
 * use our ABAC implementation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 20-5-18
 */
@Slf4j
@Configuration
@ConditionalOnClass(AbacPermissionEvaluator.class)
@AllArgsConstructor
public class AbacPermissionEvaluatorAutoConfiguration {

	private final PolicyEnforcement policyEnforcement;

	private final TimeProvider timeProvider;

	@Bean
	@ConditionalOnMissingBean(AbacPermissionEvaluator.class)
	public AbacPermissionEvaluator abacPermissionEvaluator() {
		log.debug("Creating autoconfigured abac permission evaluator");
		return new TrampolineAbacPermissionEvaluator(this.policyEnforcement,
				this.timeProvider);
	}

}
