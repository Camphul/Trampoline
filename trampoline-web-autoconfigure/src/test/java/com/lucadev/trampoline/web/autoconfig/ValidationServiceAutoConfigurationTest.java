package com.lucadev.trampoline.web.autoconfig;

import com.lucadev.trampoline.web.validation.BindingResultResponse;
import com.lucadev.trampoline.web.validation.TrampolineValidationService;
import com.lucadev.trampoline.web.validation.ValidationService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Tests to see if the autoconfig configures the correct bean.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/10/19
 */
public class ValidationServiceAutoConfigurationTest {

	private AnnotationConfigApplicationContext context;

	@Before
	public void setUp() throws Exception {
		context = new AnnotationConfigApplicationContext();
	}

	@After
	public void tearDown() throws Exception {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	public void registersValidationServiceAutomatically() {
		this.context.registerBean(ValidationServiceAutoConfiguration.class);
		this.context.refresh();

		ValidationService validationService = context.getBean(ValidationService.class);
		assertThat(validationService, instanceOf(TrampolineValidationService.class));
	}

	@Test
	public void registersCustomValidationServiceAutomatically() {
		this.context.register(CustomValidationServiceConfig.class);
		this.context.register(ValidationServiceAutoConfiguration.class);
		this.context.refresh();

		ValidationService validationService = context.getBean(ValidationService.class);
		assertThat(validationService, instanceOf(CustomValidationService.class));
	}


	@Configuration
	protected static class CustomValidationServiceConfig {
		@Bean
		public ValidationService validationService() {
			return new CustomValidationService();
		}
	}

	protected static class CustomValidationService implements ValidationService {

		@Override
		public BindingResultResponse createBindingResultResponse(BindingResult bindingResult) {
			return null;
		}
	}

}