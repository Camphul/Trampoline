package com.lucadev.trampoline.security.abac.autoconfigure;

import com.lucadev.trampoline.security.abac.policy.PolicyDefinition;
import com.lucadev.trampoline.security.abac.policy.PolicyRuleRepository;
import com.lucadev.trampoline.security.abac.policy.impl.JpaPolicyDefinition;
import com.lucadev.trampoline.security.abac.policy.impl.JsonFilePolicyDefinition;
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
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 20-5-18
 */
@Configuration
@ConditionalOnClass(PolicyDefinition.class)
public class PolicyDefinitionAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(PolicyDefinitionAutoConfiguration.class);
    private final String jsonFilePath;
    private final PolicyRuleRepository policyRuleRepository;

    @Autowired
    public PolicyDefinitionAutoConfiguration(PolicyRuleRepository policyRuleRepository,
                                             @Value("${trampoline.security.abac.policy.definition.json.filepath:default-policy.json}") String jsonFilePath) {
        this.policyRuleRepository = policyRuleRepository;
        this.jsonFilePath = jsonFilePath;
    }

    @Bean
    @ConditionalOnMissingBean(PolicyDefinition.class)
    public PolicyDefinition policyDefinition() {
        LOGGER.debug("Creating autoconfigured policy definition");
        JsonFilePolicyDefinition parent = null;
        try {
            parent = new JsonFilePolicyDefinition(jsonFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JpaPolicyDefinition(policyRuleRepository, parent);

    }
}
