package com.lucadev.trampoline.data.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;

/**
 * Autoconfiguration for trampoline-data.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Slf4j
@Configuration
@EnableJpaAuditing
@EnableCaching
public class TrampolineDataAutoConfiguration {

	@PostConstruct
	public void postConstruct() {
		log.debug("Configured trampoline data.");
	}

}
