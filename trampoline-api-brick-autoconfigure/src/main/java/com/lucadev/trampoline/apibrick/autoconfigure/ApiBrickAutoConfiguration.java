package com.lucadev.trampoline.apibrick.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Autoconfig for api brick module.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/19/20
 */
@Slf4j
@Configuration
public class ApiBrickAutoConfiguration {

	@PostConstruct
	public void postConstruct() {
		log.debug("ApiBrick autoconfig postconstruct.");
	}

}
