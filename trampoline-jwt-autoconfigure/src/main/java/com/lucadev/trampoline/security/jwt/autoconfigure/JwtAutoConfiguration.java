package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityConfigurationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * Autoconfiguration for jwt.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/28/19
 */
@Configuration
@EnableConfigurationProperties(JwtSecurityConfigurationProperties.class)
public class JwtAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public UserDetailsChecker userDetailsChecker() {
		return new AccountStatusUserDetailsChecker();
	}

}
