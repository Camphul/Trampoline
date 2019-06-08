package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.adapter.JwtConfigurationAdapter;
import com.lucadev.trampoline.security.jwt.adapter.NopJwtConfigurationAdapter;
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
public class JwtConfigurationAdapterAutoConfigurationAdapterTest {

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
		this.context.register(JwtConfigurationAdapterAutoConfiguration.class);
		this.context.refresh();
		JwtConfigurationAdapter jwtConfiguration = this.context
				.getBean(JwtConfigurationAdapter.class);
		assertThat(jwtConfiguration, instanceOf(NopJwtConfigurationAdapter.class));
	}

	@Test
	public void customJwtConfigurationBean() {
		this.context.register(SomeRandomJwtConfiguration.class);
		this.context.register(JwtConfigurationAdapterAutoConfiguration.class);
		this.context.refresh();
		JwtConfigurationAdapter jwtConfiguration = this.context
				.getBean(JwtConfigurationAdapter.class);
		assertThat(jwtConfiguration, instanceOf(SomeRandomJwtConfiguration.class));
	}

	@Configuration
	protected static class SomeRandomJwtConfiguration implements JwtConfigurationAdapter {

		@Override
		public boolean shouldIgnoreExpiration(User user) {
			return false;
		}

		@Override
		public void createToken(User user, Map<String, Object> claims) {

		}

	}

}