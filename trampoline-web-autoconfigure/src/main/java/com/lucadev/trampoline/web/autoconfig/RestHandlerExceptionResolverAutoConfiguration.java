package com.lucadev.trampoline.web.autoconfig;

import com.lucadev.trampoline.web.internal.ResponseEntityResponseProcessor;
import com.lucadev.trampoline.web.exception.handler.RestExceptionHandler;
import com.lucadev.trampoline.web.exception.resolver.RestHandlerExceptionResolver;
import com.lucadev.trampoline.web.exception.resolver.TrampolineRestHandlerExceptionResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Configuration
@ConditionalOnClass(RestHandlerExceptionResolver.class)
public class RestHandlerExceptionResolverAutoConfiguration {

	@Bean(name = "restHandlerExceptionResolver")
	public RestHandlerExceptionResolver restHandlerExceptionResolver(List<RestExceptionHandler> exceptionHandlers,
																	 ResponseEntityResponseProcessor responseProcessor) {
		return new TrampolineRestHandlerExceptionResolver(exceptionHandlers, responseProcessor);
	}
}
