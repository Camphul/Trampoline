package com.lucadev.trampoline.web.autoconfig;

import com.lucadev.trampoline.web.internal.ResponseEntityResponseProcessor;
import com.lucadev.trampoline.web.exception.handler.RestExceptionHandler;
import com.lucadev.trampoline.web.exception.resolver.RestHandlerExceptionResolver;
import com.lucadev.trampoline.web.exception.resolver.TrampolineRestHandlerExceptionResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Autoconfigures the default {@link RestHandlerExceptionResolver} implementation.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Configuration
@ConditionalOnClass(RestHandlerExceptionResolver.class)
public class RestHandlerExceptionResolverAutoConfiguration {

	/**
	 * Configure a {@link RestHandlerExceptionResolver} bean.
	 * @param exceptionHandlers list of exception handlers to add.
	 * @param responseProcessor the responseprocessor used to return correct data to the client.
	 * @return the newly created bean.
	 */
	@Bean(name = "restHandlerExceptionResolver")
	@ConditionalOnMissingBean
	public RestHandlerExceptionResolver restHandlerExceptionResolver(List<RestExceptionHandler> exceptionHandlers,
																	 ResponseEntityResponseProcessor responseProcessor) {
		return new TrampolineRestHandlerExceptionResolver(exceptionHandlers, responseProcessor);
	}
}
