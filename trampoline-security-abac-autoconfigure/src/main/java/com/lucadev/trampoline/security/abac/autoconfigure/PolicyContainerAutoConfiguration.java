package com.lucadev.trampoline.security.abac.autoconfigure;

import com.lucadev.trampoline.security.abac.PolicyContainer;
import com.lucadev.trampoline.security.abac.impl.JpaPolicyContainer;
import com.lucadev.trampoline.security.abac.impl.JsonFilePolicyContainer;
import com.lucadev.trampoline.security.abac.persistence.repository.PolicyRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Autoconfigure container for policies.
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 20-5-18
 */
@Configuration
@ConditionalOnClass(PolicyContainer.class)
public class PolicyContainerAutoConfiguration {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PolicyContainerAutoConfiguration.class);

	private final String jsonFilePath;

	private final PolicyRuleRepository policyRuleRepository;

	@Autowired
	public PolicyContainerAutoConfiguration(PolicyRuleRepository policyRuleRepository,
			@Value("${trampoline.security.abac.policy.definition.json.filepath:default-policy.json}") String jsonFilePath) {
		this.policyRuleRepository = policyRuleRepository;
		this.jsonFilePath = jsonFilePath;
	}

	@Bean
	@ConditionalOnMissingBean(PolicyContainer.class)
	public PolicyContainer policyDefinition() {
		LOGGER.debug("Creating autoconfigured policy definition");
		JsonFilePolicyContainer parent = null;
		try {
			parent = new JsonFilePolicyContainer(this.jsonFilePath);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return new JpaPolicyContainer(this.policyRuleRepository, parent);

	}

}
