package com.lucadev.trampoline.security.jwt.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configure JWT security.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Configuration
@EnableConfigurationProperties(JwtSecurityConfigurationProperties.class)
public class JwtSecurityConfiguration {

}
