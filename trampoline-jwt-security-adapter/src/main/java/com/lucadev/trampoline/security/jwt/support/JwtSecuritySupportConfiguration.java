package com.lucadev.trampoline.security.jwt.support;

import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.adapter.TokenConfigurationAdapter;
import com.lucadev.trampoline.security.jwt.authentication.TokenAuthenticationProvider;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityConfigurationProperties;
import com.lucadev.trampoline.security.jwt.support.provider.UserDetailsTokenAuthenticationProvider;
import com.lucadev.trampoline.security.jwt.support.provider.UserIdTokenConfigurationAdapter;
import com.lucadev.trampoline.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Configures to enable this module.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/3/19
 */
@RequiredArgsConstructor
@ConditionalOnClass({TokenService.class, UserService.class})
public class JwtSecuritySupportConfiguration {

	private final TokenService tokenService;

	private final UserDetailsService userDetailsService;

	private final UserDetailsChecker userDetailsChecker;

	private final JwtSecurityConfigurationProperties jwtSecurityConfigurationProperties;

	/**
	 * Create token auth provider.
	 * @return stateless auth provider.
	 */
	@Bean
	@ConditionalOnMissingBean(value = TokenAuthenticationProvider.class,
			ignored = UserDetailsTokenAuthenticationProvider.class)
	public TokenAuthenticationProvider tokenAuthenticationProvider() {
		return new UserDetailsTokenAuthenticationProvider(this.tokenService,
				this.userDetailsService, this.userDetailsChecker);
	}

	/**
	 * Creates adapter which adds user id to token.
	 * @return token config adapter.
	 */
	@Bean
	public TokenConfigurationAdapter tokenConfigurationAdapter() {
		return new UserIdTokenConfigurationAdapter(
				this.jwtSecurityConfigurationProperties);
	}

}
