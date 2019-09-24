package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityConfigurationProperties;
import com.lucadev.trampoline.security.jwt.decorator.AuthoritiesTokenDecorator;
import com.lucadev.trampoline.security.jwt.decorator.TokenDecorator;
import com.lucadev.trampoline.security.jwt.decorator.UsernameTokenDecorator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for basic JWT token decorator implementations.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/28/19
 */
@Configuration
@RequiredArgsConstructor
public class TokenDecoratorAutoConfiguration {

	private final JwtSecurityConfigurationProperties jwtSecurityConfigurationProperties;

	@Bean(name = "usernameTokenDecorator")
	@ConditionalOnMissingBean(name = "usernameTokenDecorator")
	public TokenDecorator usernameTokenDecorator() {
		return new UsernameTokenDecorator(this.jwtSecurityConfigurationProperties);
	}

	@Bean(name = "authoritiesTokenDecorator")
	@ConditionalOnMissingBean(name = "authoritiesTokenDecorator")
	public TokenDecorator authoritiesTokenDecorator() {
		return new AuthoritiesTokenDecorator(this.jwtSecurityConfigurationProperties);
	}

}
