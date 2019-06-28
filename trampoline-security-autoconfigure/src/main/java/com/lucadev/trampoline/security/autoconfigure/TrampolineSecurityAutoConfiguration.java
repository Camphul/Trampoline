package com.lucadev.trampoline.security.autoconfigure;

import com.lucadev.trampoline.security.configuration.SecurityConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfigure security.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/28/19
 */
@Configuration
@EnableConfigurationProperties(SecurityConfigurationProperties.class)
public class TrampolineSecurityAutoConfiguration {
}
