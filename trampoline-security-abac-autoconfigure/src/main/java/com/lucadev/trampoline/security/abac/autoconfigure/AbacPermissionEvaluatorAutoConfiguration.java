package com.lucadev.trampoline.security.abac.autoconfigure;

import com.lucadev.trampoline.security.abac.AbacPermissionEvaluator;
import com.lucadev.trampoline.security.abac.PolicyEnforcement;
import com.lucadev.trampoline.security.abac.impl.TrampolineAbacPermissionEvaluator;
import com.lucadev.trampoline.service.time.TimeProvider;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 20-5-18
 */
@Configuration
@ConditionalOnClass(AbacPermissionEvaluator.class)
@AllArgsConstructor
public class AbacPermissionEvaluatorAutoConfiguration {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbacPermissionEvaluatorAutoConfiguration.class);

	private final PolicyEnforcement policyEnforcement;

	private final TimeProvider timeProvider;

	@Bean
	@ConditionalOnMissingBean(AbacPermissionEvaluator.class)
	public AbacPermissionEvaluator abacPermissionEvaluator() {
		LOGGER.debug("Creating autoconfigured abac permission evaluator");
		return new TrampolineAbacPermissionEvaluator(policyEnforcement, timeProvider);
	}

}
