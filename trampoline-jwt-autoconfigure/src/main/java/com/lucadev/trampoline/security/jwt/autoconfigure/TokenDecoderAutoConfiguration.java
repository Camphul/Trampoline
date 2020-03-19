package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.JwtTokenDecoder;
import com.lucadev.trampoline.security.jwt.TokenDecoder;
import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Token decoder autoconfig.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/19/20
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass(TokenService.class)
public class TokenDecoderAutoConfiguration {

	private final JwtSecurityConfigurationProperties properties;

	@Bean
	@ConditionalOnMissingBean
	public TokenDecoder tokenDecoder() {
		log.debug("Initializing token decoder.");
		return new JwtTokenDecoder(this.properties);
	}

}
