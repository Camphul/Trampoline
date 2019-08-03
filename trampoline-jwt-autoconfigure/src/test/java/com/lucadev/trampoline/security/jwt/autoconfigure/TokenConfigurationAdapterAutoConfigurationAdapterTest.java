package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.adapter.NopTokenConfigurationAdapter;
import com.lucadev.trampoline.security.jwt.adapter.TokenConfigurationAdapter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class TokenConfigurationAdapterAutoConfigurationAdapterTest {

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
	public void registersDefaultTokenConfigurationAutomatically() {
		this.context.register(TokenConfigurationAdapterAutoConfiguration.class);
		this.context.refresh();
		TokenConfigurationAdapter tokenConfiguration = this.context
				.getBean(TokenConfigurationAdapter.class);
		assertThat(tokenConfiguration, instanceOf(NopTokenConfigurationAdapter.class));
	}

	@Test
	public void customTokenConfigurationBean() {
		this.context.register(SomeRandomTokenConfiguration.class);
		this.context.register(TokenConfigurationAdapterAutoConfiguration.class);
		this.context.refresh();
		TokenConfigurationAdapter tokenConfiguration = this.context
				.getBean(TokenConfigurationAdapter.class);
		assertThat(tokenConfiguration, instanceOf(SomeRandomTokenConfiguration.class));
	}

	@Configuration
	protected static class SomeRandomTokenConfiguration implements TokenConfigurationAdapter {

		@Override
		public boolean shouldIgnoreExpiration(UserDetails user) {
			return false;
		}

		@Override
		public void createToken(UserDetails user, Map<String, Object> claims) {

		}

	}

}