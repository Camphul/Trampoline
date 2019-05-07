package com.lucadev.trampoline.data.web.configuration;

import com.lucadev.trampoline.data.web.FindByIdMethodArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Configure required components for web integration.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 5/7/19
 */
@Configuration
@RequiredArgsConstructor
public class TrampolineDataWebMvcConfiguration implements WebMvcConfigurer, Ordered {

	private static final Logger LOGGER = LoggerFactory.getLogger(TrampolineDataWebMvcConfiguration.class);
	private final EntityManager entityManager;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new FindByIdMethodArgumentResolver(entityManager));
		LOGGER.debug("Added custom argument resolvers.");
	}

	@Override
	public int getOrder() {
		return 34;
	}
}
