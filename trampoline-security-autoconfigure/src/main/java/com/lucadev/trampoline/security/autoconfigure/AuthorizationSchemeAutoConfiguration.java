package com.lucadev.trampoline.security.autoconfigure;

import com.lucadev.trampoline.security.configuration.AuthorizationSchemeConfiguration;
import com.lucadev.trampoline.security.configuration.NOPAuthorizationSchemeConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Configuration
@ConditionalOnClass(AuthorizationSchemeConfiguration.class)
public class AuthorizationSchemeAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationSchemeAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public AuthorizationSchemeConfiguration authorizationSchemeBuilderConfiguration() {
        LOGGER.debug("Using default AuthorizationSchemeConfiguration");
        return new NOPAuthorizationSchemeConfiguration();
    }

}
