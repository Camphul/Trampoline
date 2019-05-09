package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.configuration.DefaultJwtConfigurationAdapter;
import com.lucadev.trampoline.security.jwt.configuration.JwtConfigurationAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autconfigure default adapter for jwt security if no beans are found.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 22-5-18
 */
@Configuration
@ConditionalOnClass(JwtConfigurationAdapter.class)
public class JwtConfigurationAdapterAutoConfiguration {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JwtConfigurationAdapterAutoConfiguration.class);

	@Bean
	@ConditionalOnMissingBean
	public JwtConfigurationAdapter jwtConfiguration() {
		LOGGER.debug("Using default jwt configuration");
		return new DefaultJwtConfigurationAdapter();
	}

}
