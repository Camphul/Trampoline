package com.lucadev.trampoline.security.autoconfigure;

import com.lucadev.trampoline.security.service.PersistentUserDetailsService;
import com.lucadev.trampoline.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Spring {@link WebSecurityConfigurerAdapter} to configure our own simple user details implementation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class PersistentUserDetailsServiceAutoConfiguration {

	private final UserService userService;

	@Bean
	@ConditionalOnMissingBean
	public UserDetailsService userDetailsService() {
		return new PersistentUserDetailsService(this.userService);
	}

}
