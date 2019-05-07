package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.configuration.DefaultJwtConfiguration;
import com.lucadev.trampoline.security.jwt.configuration.JwtConfiguration;
import com.lucadev.trampoline.security.persistence.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class JwtConfigurationAutoConfigurationTest {
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
	public void registersDefaultJwtConfigurationAutomatically() {
		this.context.register(JwtConfigurationAutoConfiguration.class);
		this.context.refresh();
		JwtConfiguration jwtConfiguration = this.context.getBean(JwtConfiguration.class);
		assertThat(jwtConfiguration, instanceOf(DefaultJwtConfiguration.class));
	}

	@Test
	public void customJwtConfigurationBean() {
		this.context.register(SomeRandomJwtConfiguration.class);
		this.context.register(JwtConfigurationAutoConfiguration.class);
		this.context.refresh();
		JwtConfiguration jwtConfiguration = this.context.getBean(JwtConfiguration.class);
		assertThat(jwtConfiguration, instanceOf(SomeRandomJwtConfiguration.class));
	}

	@Configuration
	protected static class SomeRandomJwtConfiguration implements JwtConfiguration {

		@Override
		public boolean getIgnoreExpirationFlag(User user) {
			return false;
		}

		@Override
		public boolean getImpersonateFlag(User user) {
			return false;
		}

		@Override
		public void createToken(User user, Map<String, Object> claims) {

		}

	}
}