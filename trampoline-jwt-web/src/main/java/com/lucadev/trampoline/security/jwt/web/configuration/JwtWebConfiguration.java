package com.lucadev.trampoline.security.jwt.web.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Configuration for JWT web module.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtWebConfigurationProperties.class)
public class JwtWebConfiguration {

	private final JwtWebConfigurationProperties configurationProperties;

	@PostConstruct
	public void postConstruct() {
		log.debug("Jwt web configuration properties: {}", configurationProperties);
	}

}