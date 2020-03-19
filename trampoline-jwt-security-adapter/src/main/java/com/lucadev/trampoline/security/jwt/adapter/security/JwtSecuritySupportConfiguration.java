package com.lucadev.trampoline.security.jwt.adapter.security;

import com.lucadev.trampoline.security.jwt.TokenDecoder;
import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.adapter.security.provider.UserDetailsTokenAuthenticationProvider;
import com.lucadev.trampoline.security.jwt.adapter.security.provider.UserIdTokenDecorator;
import com.lucadev.trampoline.security.jwt.authentication.TokenAuthenticationProvider;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityConfigurationProperties;
import com.lucadev.trampoline.security.jwt.decorator.TokenDecorator;
import com.lucadev.trampoline.security.service.UserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configures to enable this module.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/3/19
 */
@ConditionalOnClass({ TokenService.class, UserService.class })
public class JwtSecuritySupportConfiguration {

	/**
	 * Create token auth provider.
	 *
	 * @param tokenService       token service.
	 * @param userDetailsService user details service which is persistent.
	 * @param userDetailsChecker checks if the user is allowed access.
	 * @param passwordEncoder    the password encoding for hashing passwords.
	 * @param tokenDecoder       jwt token decoder.
	 * @return stateless auth provider.
	 */
	@Bean(name = "tokenAuthenticationProvider")
	@ConditionalOnMissingBean(value = TokenAuthenticationProvider.class,
			ignored = UserDetailsTokenAuthenticationProvider.class)
	public TokenAuthenticationProvider tokenAuthenticationProvider(
			TokenService tokenService, UserDetailsService userDetailsService,
			UserDetailsChecker userDetailsChecker, PasswordEncoder passwordEncoder,
			TokenDecoder tokenDecoder) {
		return new UserDetailsTokenAuthenticationProvider(tokenService,
				userDetailsService, userDetailsChecker, passwordEncoder, tokenDecoder);
	}

	/**
	 * Creates adapter which adds user id to token.
	 * @param jwtSecurityConfigurationProperties jwt config properties.
	 * @return token config adapter.
	 */
	@Bean(name = "userIdTokenDecorator")
	@ConditionalOnMissingBean(name = "userIdTokenDecorator")
	public TokenDecorator userIdTokenDecorator(
			JwtSecurityConfigurationProperties jwtSecurityConfigurationProperties) {
		return new UserIdTokenDecorator(jwtSecurityConfigurationProperties);
	}

}
