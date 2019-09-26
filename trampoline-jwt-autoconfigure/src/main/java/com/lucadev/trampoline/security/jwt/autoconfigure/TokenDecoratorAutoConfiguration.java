package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityConfigurationProperties;
import com.lucadev.trampoline.security.jwt.decorator.AuthoritiesTokenDecorator;
import com.lucadev.trampoline.security.jwt.decorator.TokenDecorator;
import com.lucadev.trampoline.security.jwt.decorator.UsernameTokenDecorator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
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
@ConditionalOnClass(TokenService.class)
public class TokenDecoratorAutoConfiguration {

	private final JwtSecurityConfigurationProperties jwtSecurityConfigurationProperties;

	/**
	 * A {@link TokenDecorator} which adds the username to the token. Only adds the bean
	 * when no other "usernameTokenDecorator" exists.
	 * @return {@link UsernameTokenDecorator} bean.
	 */
	@Bean(name = "usernameTokenDecorator")
	@ConditionalOnMissingBean(name = "usernameTokenDecorator")
	public TokenDecorator usernameTokenDecorator() {
		return new UsernameTokenDecorator(this.jwtSecurityConfigurationProperties);
	}

	/**
	 * A {@link TokenDecorator} which adds the authorities to the token. Only adds the
	 * bean when no other "authoritiesTokenDecorator" exists.
	 * @return {@link AuthoritiesTokenDecorator} bean.
	 */
	@Bean(name = "authoritiesTokenDecorator")
	@ConditionalOnMissingBean(name = "authoritiesTokenDecorator")
	public TokenDecorator authoritiesTokenDecorator() {
		return new AuthoritiesTokenDecorator(this.jwtSecurityConfigurationProperties);
	}

}
