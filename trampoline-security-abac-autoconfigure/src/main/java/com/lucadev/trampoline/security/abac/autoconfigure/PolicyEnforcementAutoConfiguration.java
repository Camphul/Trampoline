package com.lucadev.trampoline.security.abac.autoconfigure;

import com.lucadev.trampoline.security.abac.PolicyContainer;
import com.lucadev.trampoline.security.abac.PolicyEnforcement;
import com.lucadev.trampoline.security.abac.impl.TrampolinePolicyEnforcement;
import com.lucadev.trampoline.security.abac.spel.context.SecurityAccessContextFactory;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfigure policy enforcer.
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 20-5-18
 */
@Configuration
@ConditionalOnClass(PolicyEnforcement.class)
@AllArgsConstructor
public class PolicyEnforcementAutoConfiguration {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PolicyEnforcementAutoConfiguration.class);

	private final SecurityAccessContextFactory securityAccessContextFactory;

	private final PolicyContainer policyContainer;

	@Bean
	@ConditionalOnMissingBean(PolicyEnforcement.class)
	public PolicyEnforcement policyEnforcement() {
		LOGGER.debug("Creating autoconfigured policy enforcement");
		return new TrampolinePolicyEnforcement(this.securityAccessContextFactory,
				this.policyContainer);
	}

}
