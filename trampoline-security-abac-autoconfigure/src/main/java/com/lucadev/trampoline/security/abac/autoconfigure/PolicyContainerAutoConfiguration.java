package com.lucadev.trampoline.security.abac.autoconfigure;

import com.lucadev.trampoline.security.abac.PolicyContainer;
import com.lucadev.trampoline.security.abac.configuration.AbacSecurityConfigurationProperties;
import com.lucadev.trampoline.security.abac.impl.JpaPolicyContainer;
import com.lucadev.trampoline.security.abac.impl.JsonFilePolicyContainer;
import com.lucadev.trampoline.security.abac.persistence.repository.PolicyRuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Autoconfigure container for policies.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 20-5-18
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass(PolicyContainer.class)
public class PolicyContainerAutoConfiguration {

	private final AbacSecurityConfigurationProperties abacSecurityConfigurationProperties;

	private final PolicyRuleRepository policyRuleRepository;

	@Bean
	@ConditionalOnMissingBean(PolicyContainer.class)
	public PolicyContainer policyDefinition() {
		log.debug("Creating autoconfigured policy definition");
		if (this.abacSecurityConfigurationProperties.isImportJson()) {
			log.debug("Importing policies from specified json file.");
			JsonFilePolicyContainer parent = null;
			try {
				parent = new JsonFilePolicyContainer(this.abacSecurityConfigurationProperties);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			return new JpaPolicyContainer(this.policyRuleRepository, parent);
		}
		else {
			log.debug("Creating JPA policy container without imports.");
			return new JpaPolicyContainer(this.policyRuleRepository);
		}

	}

}
