package com.lucadev.trampoline.web.configuration;

import com.lucadev.trampoline.web.exception.resolver.RestHandlerExceptionResolver;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.util.List;

import static com.lucadev.trampoline.web.configuration.TrampolineWebMvcConfiguration.TRAMPOLINE_WEB_MVC_CONFIG_ORDER;

/**
 * Configures a new {@link HandlerExceptionResolver}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Configuration
@Order(TRAMPOLINE_WEB_MVC_CONFIG_ORDER)
@AllArgsConstructor
public class TrampolineWebMvcConfiguration implements WebMvcConfigurer {

	public static final int TRAMPOLINE_WEB_MVC_CONFIG_ORDER = 60;
	private final RestHandlerExceptionResolver restHandlerExceptionResolver;

	/**
	 * Removes {@link DefaultHandlerExceptionResolver} and replaces with our drop in {@link RestHandlerExceptionResolver}
	 *
	 * @param resolvers the {@link List} of existing {@link HandlerExceptionResolver}
	 */
	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
		resolvers.removeIf(handler -> (handler instanceof DefaultHandlerExceptionResolver));

		resolvers.add(restHandlerExceptionResolver);
	}
}
