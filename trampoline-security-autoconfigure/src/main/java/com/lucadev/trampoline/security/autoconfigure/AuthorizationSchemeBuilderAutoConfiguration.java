package com.lucadev.trampoline.security.autoconfigure;

import com.lucadev.trampoline.security.configuration.AuthorizationSchemeBuilderConfiguration;
import com.lucadev.trampoline.security.configuration.NOPAuthorizationSchemeBuilderConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Configuration
@ConditionalOnClass(AuthorizationSchemeBuilderConfiguration.class)
public class AuthorizationSchemeBuilderAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationSchemeBuilderAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public AuthorizationSchemeBuilderConfiguration authorizationSchemeBuilderConfiguration() {
        LOGGER.debug("Using default AuthorizationSchemeBuilderConfiguration");
        return new NOPAuthorizationSchemeBuilderConfiguration();
    }

}
