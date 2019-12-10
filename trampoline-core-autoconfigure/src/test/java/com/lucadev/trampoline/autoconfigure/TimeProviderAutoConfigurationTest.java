package com.lucadev.trampoline.autoconfigure;

import com.lucadev.trampoline.service.time.SystemTimeProvider;
import com.lucadev.trampoline.service.time.TimeProvider;
import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class TimeProviderAutoConfigurationTest {

	@Test
	public void registersSystemTimeProviderAutomatically() {
		new ApplicationContextRunner()
				.withConfiguration(
						AutoConfigurations.of(TimeProviderAutoConfiguration.class))
				.run(context -> {
					TimeProvider timeProvider = context.getBean(TimeProvider.class);
					assertThat(timeProvider, instanceOf(SystemTimeProvider.class));
				});
	}

	@Test
	public void customTimeProviderBean() {
		new ApplicationContextRunner()
				.withConfiguration(AutoConfigurations.of(TimeProviderConfiguration.class))
				.withConfiguration(
						AutoConfigurations.of(TimeProviderAutoConfiguration.class))
				.run(context -> {
					TimeProvider timeProvider = context.getBean(TimeProvider.class);
					assertThat(timeProvider, not(instanceOf(SystemTimeProvider.class)));
					assertEquals(0L, timeProvider.unix());
				});
	}

	/**
	 * Custom config to override default bean
	 */
	@Configuration
	protected static class TimeProviderConfiguration {

		@Bean
		public TimeProvider timeProvider() {
			return new TimeProvider() {
				@Override
				public Instant now() {
					return Instant.now();
				}

				@Override
				public long unix() {
					return 0;
				}
			};
		}

	}

}