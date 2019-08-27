package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.JwtTokenService;
import com.lucadev.trampoline.security.jwt.TokenPayload;
import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.adapter.TokenConfigurationAdapter;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityConfigurationProperties;
import com.lucadev.trampoline.service.time.TimeProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class TokenServiceAutoConfigurationTest {

	private AnnotationConfigApplicationContext context;

	private TokenConfigurationAdapter jwtConfiguration;

	private JwtSecurityConfigurationProperties jwtSecurityConfigurationProperties;

	private TimeProvider timeProvider;

	@Before
	public void setUp() throws Exception {
		jwtSecurityConfigurationProperties = mock(
				JwtSecurityConfigurationProperties.class);
		when(jwtSecurityConfigurationProperties.getSecret())
				.thenReturn("averylongstringasjwtsecurity");
		timeProvider = mock(TimeProvider.class);
		jwtConfiguration = mock(TokenConfigurationAdapter.class);
		context = new AnnotationConfigApplicationContext();
	}

	@After
	public void tearDown() throws Exception {
		if (this.context != null) {
			this.context.close();
		}
		if (jwtSecurityConfigurationProperties != null) {
			jwtSecurityConfigurationProperties = null;
		}
		if (timeProvider != null) {
			timeProvider = null;
		}

		if (jwtConfiguration != null) {
			jwtConfiguration = null;
		}
	}

	@Test
	public void registersJwtTokenServiceAutomatically() {
		this.context.registerBean(TokenServiceAutoConfiguration.class, jwtConfiguration,
				timeProvider, jwtSecurityConfigurationProperties);
		this.context.refresh();
		TokenService tokenService = this.context.getBean(TokenService.class);
		assertThat(tokenService, instanceOf(JwtTokenService.class));
	}

	@Test
	public void customTokenServiceBean() {
		this.context.register(CustomTokenServiceConfig.class);
		this.context.registerBean(TokenServiceAutoConfiguration.class, jwtConfiguration,
				timeProvider, jwtSecurityConfigurationProperties);
		this.context.refresh();
		TokenService tokenService = this.context.getBean(TokenService.class);
		assertThat(tokenService, instanceOf(TestTokenService.class));
	}

	@Configuration
	protected static class CustomTokenServiceConfig {

		@Bean
		public TokenService tokenService() {
			return new TestTokenService();
		}

	}

	protected static class TestTokenService implements TokenService {

		@Override
		public String issueToken(UserDetails user) {
			return null;
		}

		@Override
		public String issueTokenRefresh(String token) {
			return null;
		}

		@Override
		public TokenPayload decodeTokenHeader(String token) {
			return null;
		}

		@Override
		public TokenPayload decodeToken(String token) {
			return null;
		}

		@Override
		public boolean isValidToken(TokenPayload tokenPayload, UserDetails user) {
			return false;
		}

		@Override
		public String issueTokenRefresh(HttpServletRequest request) {
			return null;
		}

		@Override
		public String getTokenHeader(HttpServletRequest request) {
			return null;
		}

	}

}
