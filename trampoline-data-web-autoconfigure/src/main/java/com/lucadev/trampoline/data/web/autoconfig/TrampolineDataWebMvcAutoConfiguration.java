package com.lucadev.trampoline.data.web.autoconfig;

import com.lucadev.trampoline.data.web.FindByIdMethodArgumentResolver;
import com.lucadev.trampoline.data.web.PrimaryKeyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
@AutoConfigureAfter(TrampolineDataWebAutoConfiguration.class)
@ConditionalOnBean(PrimaryKeyMapper.class)
@RequiredArgsConstructor
public class TrampolineDataWebMvcAutoConfiguration implements WebMvcConfigurer, Ordered {

	/**
	 * Configuration load order.
	 */
	public static final int DATA_WEB_CONFIGURATION_ORDER = 30;

	private final EntityManager entityManager;

	private final PrimaryKeyMapper primaryKeyMapper;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new FindByIdMethodArgumentResolver(this.entityManager,
				this.primaryKeyMapper));
		log.debug("Added custom argument resolvers.");
	}

	@Override
	public int getOrder() {
		return DATA_WEB_CONFIGURATION_ORDER;
	}

}
