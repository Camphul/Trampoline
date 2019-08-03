package com.lucadev.trampoline.security.jwt.support;

import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.adapter.TokenConfigurationAdapter;
import com.lucadev.trampoline.security.jwt.authentication.TokenAuthenticationProvider;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityConfigurationProperties;
import com.lucadev.trampoline.security.jwt.support.provider.UserDetailsTokenAuthenticationProvider;
import com.lucadev.trampoline.security.jwt.support.provider.UserIdTokenConfigurationAdapter;
import com.lucadev.trampoline.security.service.UserService;
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
@ConditionalOnClass({TokenService.class, UserService.class})
public class JwtSecuritySupportConfiguration {

	@Bean
	@ConditionalOnMissingBean(value = TokenAuthenticationProvider.class,
			ignored = UserDetailsTokenAuthenticationProvider.class)
	public TokenAuthenticationProvider tokenAuthenticationProvider(
			TokenService tokenService, UserDetailsService userDetailsService,
			UserDetailsChecker userDetailsChecker) {
		return new UserDetailsTokenAuthenticationProvider(tokenService,
				userDetailsService, userDetailsChecker);
	}

	@Bean
	public TokenConfigurationAdapter tokenConfigurationAdapter(
			JwtSecurityConfigurationProperties jwtSecurityConfigurationProperties) {
		return new UserIdTokenConfigurationAdapter(jwtSecurityConfigurationProperties);
	}

}
