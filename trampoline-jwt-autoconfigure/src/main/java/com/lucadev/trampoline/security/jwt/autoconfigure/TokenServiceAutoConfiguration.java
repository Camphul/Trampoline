package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.JwtTokenService;
import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityConfigurationProperties;
import com.lucadev.trampoline.security.jwt.decorator.TokenDecorator;
import com.lucadev.trampoline.service.time.TimeProvider;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Autoconfig which configures the default {@link TokenService} bean if none is
 * configured.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@ConditionalOnClass(TokenService.class)
@EnableConfigurationProperties(JwtSecurityConfigurationProperties.class)
@AllArgsConstructor
public class TokenServiceAutoConfiguration {

	private final List<TokenDecorator> tokenDecorators;

	private final JwtSecurityConfigurationProperties jwtSecurityConfigurationProperties;

	private final TimeProvider timeProvider;

	@Bean
	@ConditionalOnMissingBean(TokenService.class)
	public TokenService tokenService() {
		return new JwtTokenService(this.tokenDecorators, this.timeProvider,
				this.jwtSecurityConfigurationProperties);
	}

}
