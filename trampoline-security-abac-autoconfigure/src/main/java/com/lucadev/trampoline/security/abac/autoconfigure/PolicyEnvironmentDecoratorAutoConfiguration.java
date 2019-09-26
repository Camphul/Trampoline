package com.lucadev.trampoline.security.abac.autoconfigure;

import com.lucadev.trampoline.security.abac.decorator.PolicyEnvironmentDecorator;
import com.lucadev.trampoline.security.abac.decorator.TimePolicyEnvironmentDecorator;
import com.lucadev.trampoline.service.time.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfigures policy environment decorators.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 9/26/19
 */
@Configuration
@AutoConfigureBefore(AbacPermissionEvaluatorAutoConfiguration.class)
@RequiredArgsConstructor
public class PolicyEnvironmentDecoratorAutoConfiguration {

	private final TimeProvider timeProvider;

	/**
	 * Create decorator which adds time to the environment.
	 *
	 * @return time decorator.
	 */
	@Bean(name = "timePolicyEnvironmentDecorator")
	@ConditionalOnMissingBean(name = "timePolicyEnvironmentDecorator")
	public PolicyEnvironmentDecorator timePolicyEnvironmentDecorator() {
		return new TimePolicyEnvironmentDecorator(this.timeProvider);
	}

}
