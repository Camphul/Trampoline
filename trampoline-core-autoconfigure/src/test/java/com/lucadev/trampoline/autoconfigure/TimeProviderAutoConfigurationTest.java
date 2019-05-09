package com.lucadev.trampoline.autoconfigure;

import com.lucadev.trampoline.service.time.SystemTimeProvider;
import com.lucadev.trampoline.service.time.TimeProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class TimeProviderAutoConfigurationTest {

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
	public void registersSystemTimeProviderAutomatically() {
		this.context.register(TimeProviderAutoConfiguration.class);
		this.context.refresh();
		TimeProvider timeProvider = this.context.getBean(TimeProvider.class);
		assertThat(timeProvider, instanceOf(SystemTimeProvider.class));
	}

	@Test
	public void customTimeProviderBean() {
		this.context.register(TimeProviderConfiguration.class,
				TimeProviderAutoConfiguration.class);
		this.context.refresh();
		TimeProvider timeProvider = this.context.getBean(TimeProvider.class);
		assertThat(timeProvider, not(instanceOf(SystemTimeProvider.class)));
		assertEquals(0L, timeProvider.unix());
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
				public Date now() {
					return new Date(0);
				}

				@Override
				public long unix() {
					return 0;
				}
			};
		}

	}

}