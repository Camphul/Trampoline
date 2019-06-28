package com.lucadev.trampoline.security.abac.autoconfigure;

import com.lucadev.trampoline.security.abac.configuration.AbacSecurityConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Configures ABAC security.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(AbacSecurityConfigurationProperties.class)
public class AbacSecurityAutoConfiguration {

	private final AbacSecurityConfigurationProperties abacSecurityConfigurationProperties;

	@PostConstruct
	public void postConstruct() {
		log.debug("Configured ABAC: {}", this.abacSecurityConfigurationProperties);
	}

}
