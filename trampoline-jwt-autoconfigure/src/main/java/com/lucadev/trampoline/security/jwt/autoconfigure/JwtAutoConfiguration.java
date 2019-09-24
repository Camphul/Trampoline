package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for jwt.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/28/19
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtSecurityConfigurationProperties.class)
public class JwtAutoConfiguration {

}
