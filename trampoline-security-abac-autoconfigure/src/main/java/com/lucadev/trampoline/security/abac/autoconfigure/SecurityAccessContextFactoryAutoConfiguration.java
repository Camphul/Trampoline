package com.lucadev.trampoline.security.abac.autoconfigure;

import com.lucadev.trampoline.security.abac.context.SecurityAccessContextFactory;
import com.lucadev.trampoline.security.abac.context.impl.TrampolineSecurityAccessContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for {@link SecurityAccessContextFactory}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 20-5-18
 */
@Configuration
@ConditionalOnClass(SecurityAccessContextFactory.class)
public class SecurityAccessContextFactoryAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityAccessContextFactoryAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(SecurityAccessContextFactory.class)
    public SecurityAccessContextFactory securityAccessContextFactory() {
        LOGGER.debug("Creating autoconfigured security access context factory");
        return new TrampolineSecurityAccessContextFactory();
    }


}
