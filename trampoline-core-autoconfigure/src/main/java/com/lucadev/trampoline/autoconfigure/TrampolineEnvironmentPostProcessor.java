package com.lucadev.trampoline.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Autoconfigure bean overrides to true so we do not have to set it in application
 * properties.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/28/19
 */
@Slf4j
public class TrampolineEnvironmentPostProcessor
		implements EnvironmentPostProcessor, Ordered {

	/**
	 * Configure bean override.
	 * @param environment config env.
	 * @param application our app.
	 */
	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment,
			SpringApplication application) {
		log.debug("Configuring trampoline environment.");
		application.setAllowBeanDefinitionOverriding(true);
	}

	/**
	 * Trampoline environment post processor load order.
	 * @return load order.
	 */
	@Override
	public int getOrder() {
		return 30;
	}

}
