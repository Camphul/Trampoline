package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.adapter.JwtConfigurationAdapter;
import com.lucadev.trampoline.security.jwt.adapter.NopJwtConfigurationAdapter;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Configuration
@ConditionalOnClass(JwtConfigurationAdapter.class)
public class JwtConfigurationAdapterAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(JwtConfigurationAdapter.class)
	public JwtConfigurationAdapter jwtConfiguration() {
		log.debug("Using default jwt configuration");
		return new NopJwtConfigurationAdapter();
	}

}
