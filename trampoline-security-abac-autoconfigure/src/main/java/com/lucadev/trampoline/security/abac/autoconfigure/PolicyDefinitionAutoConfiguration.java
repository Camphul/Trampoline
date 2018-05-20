package com.lucadev.trampoline.security.abac.autoconfigure;

import com.lucadev.trampoline.security.abac.policy.PolicyDefinition;
import com.lucadev.trampoline.security.abac.policy.impl.JsonFilePolicyDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 20-5-18
 */
@Configuration
@ConditionalOnClass(PolicyDefinition.class)
public class PolicyDefinitionAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(PolicyDefinitionAutoConfiguration.class);
    private final String jsonFilePath;

    @Autowired
    public PolicyDefinitionAutoConfiguration(@Value("${trampoline.security.abac.policy.definition.json.filepath:default-policy.json}") String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
    }

    @Bean
    @ConditionalOnMissingBean(PolicyDefinition.class)
    public PolicyDefinition policyDefinition() {
        LOGGER.debug("Creating autoconfigured policy definition");
        return new JsonFilePolicyDefinition(jsonFilePath);
    }

}
