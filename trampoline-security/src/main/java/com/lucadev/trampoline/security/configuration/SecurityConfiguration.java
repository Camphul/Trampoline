package com.lucadev.trampoline.security.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Configures authentication related components.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SecurityConfigurationProperties.class)
public class SecurityConfiguration {

	private final SecurityConfigurationProperties securityConfigurationProperties;

	@PostConstruct
	public void postConstruct() {
		log.debug("Configured security: {}", securityConfigurationProperties);
	}
}
