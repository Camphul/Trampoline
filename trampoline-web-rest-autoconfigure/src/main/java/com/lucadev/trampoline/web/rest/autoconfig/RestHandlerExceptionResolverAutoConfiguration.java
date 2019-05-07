package com.lucadev.trampoline.web.rest.autoconfig;

import com.lucadev.trampoline.web.rest.error.exception.handler.RestExceptionHandler;
import com.lucadev.trampoline.web.rest.error.exception.internal.ResponseEntityResponseProcessor;
import com.lucadev.trampoline.web.rest.error.exception.resolver.RestHandlerExceptionResolver;
import com.lucadev.trampoline.web.rest.error.exception.resolver.TrampolineRestHandlerExceptionResolver;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Autoconfigures the default {@link RestHandlerExceptionResolver} implementation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/9/19
 */
@AllArgsConstructor
@Configuration
@ConditionalOnClass(RestHandlerExceptionResolver.class)
public class RestHandlerExceptionResolverAutoConfiguration {

	private final List<RestExceptionHandler> exceptionHandlers;
	private final ResponseEntityResponseProcessor responseProcessor;

	/**
	 * Configure a {@link RestHandlerExceptionResolver} bean.
	 *
	 * @return the newly created bean.
	 */
	@Bean(name = "restHandlerExceptionResolver")
	@ConditionalOnMissingBean
	public RestHandlerExceptionResolver restHandlerExceptionResolver() {
		return new TrampolineRestHandlerExceptionResolver(exceptionHandlers, responseProcessor);
	}

}
