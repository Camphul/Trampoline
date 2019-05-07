package com.lucadev.trampoline.security.abac.autoconfigure;

import com.lucadev.trampoline.security.abac.PolicyContainer;
import com.lucadev.trampoline.security.abac.decision.PolicyDecisionManager;
import com.lucadev.trampoline.security.abac.PolicyInformationProvider;
import com.lucadev.trampoline.security.abac.decision.TrampolinePolicyDecisionManager;
import com.lucadev.trampoline.security.abac.impl.TrampolinePolicyInformationProvider;
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
@ConditionalOnClass(PolicyDecisionManager.class)
@AllArgsConstructor
public class PolicyDecisionManagerAutoConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(PolicyDecisionManagerAutoConfiguration.class);
	private final PolicyContainer policyContainer;

	@Bean
	@ConditionalOnMissingBean(PolicyDecisionManager.class)
	public PolicyDecisionManager policyDecisionManager() {
		LOGGER.debug("Creating policy decision manager.");
		return new TrampolinePolicyDecisionManager(policyContainer);
	}

}
