package com.lucadev.trampoline.data.web.configuration;

import com.lucadev.trampoline.data.web.FindByIdMethodArgumentResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Configure required components for web integration.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/7/19
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class TrampolineDataWebMvcConfiguration implements WebMvcConfigurer, Ordered {

	/**
	 * Configuration load order.
	 */
	public static final int DATA_WEB_CONFIGURATION_ORDER = 30;

	private final EntityManager entityManager;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new FindByIdMethodArgumentResolver(this.entityManager));
		log.debug("Added custom argument resolvers.");
	}

	@Override
	public int getOrder() {
		return DATA_WEB_CONFIGURATION_ORDER;
	}

}
