package com.lucadev.trampoline.web.autoconfigure;

import com.lucadev.trampoline.web.error.TrampolineResponseEntityExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Autoconfigures default Trampoline response entity exception handler.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 10/15/19
 */
@Configuration
@ConditionalOnWebApplication // Requires a webapp
@ConditionalOnClass(TrampolineResponseEntityExceptionHandler.class/*
																	 * Dont event try when
																	 * this isnt available
																	 */)
@ConditionalOnMissingBean(annotation = ControllerAdvice.class) // Check if no other class
// uses @ControllerAdvice
// already
public class ResponseEntityExceptionHandlerAutoConfiguration {

	/**
	 * Create default exception handler/controller advice which returns standardized
	 * responses.
	 *
	 * @return trampoline {@link ResponseEntityExceptionHandler}
	 */
	@Bean
	public ResponseEntityExceptionHandler responseEntityExceptionHandler() {
		// Create custom implementation since we require an abstract one to prevent
		// default bean init.
		return new TrampolineResponseEntityExceptionHandler() {
		};
	}

}
