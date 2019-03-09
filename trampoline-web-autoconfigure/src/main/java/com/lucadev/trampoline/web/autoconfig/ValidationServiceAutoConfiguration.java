package com.lucadev.trampoline.web.autoconfig;

import com.lucadev.trampoline.web.validation.service.TrampolineValidationService;
import com.lucadev.trampoline.web.validation.service.ValidationService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfigure {@link ValidationService}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Configuration
@ConditionalOnClass(ValidationService.class)
public class ValidationServiceAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public ValidationService validationService() {
		return new TrampolineValidationService();
	}
}
