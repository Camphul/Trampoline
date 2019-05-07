package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.configuration.DefaultJwtConfiguration;
import com.lucadev.trampoline.security.jwt.configuration.JwtConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 22-5-18
 */
@Configuration
@ConditionalOnClass(JwtConfiguration.class)
public class JwtConfigurationAutoConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtConfigurationAutoConfiguration.class);


	@Bean
	@ConditionalOnMissingBean
	public JwtConfiguration jwtConfiguration() {
		LOGGER.debug("Using default jwt configuration");
		return new DefaultJwtConfiguration();
	}

}
