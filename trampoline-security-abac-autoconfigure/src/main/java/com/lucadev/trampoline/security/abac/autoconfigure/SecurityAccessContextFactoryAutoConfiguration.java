package com.lucadev.trampoline.security.abac.autoconfigure;

import com.lucadev.trampoline.security.abac.spel.context.SecurityAccessContextFactory;
import com.lucadev.trampoline.security.abac.spel.context.impl.TrampolineSecurityAccessContextFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for {@link SecurityAccessContextFactory}.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 20-5-18
 */
@Slf4j
@Configuration
@ConditionalOnClass(SecurityAccessContextFactory.class)
public class SecurityAccessContextFactoryAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(SecurityAccessContextFactory.class)
	public SecurityAccessContextFactory securityAccessContextFactory() {
		log.debug("Creating autoconfigured security access context factory");
		return new TrampolineSecurityAccessContextFactory();
	}

}
