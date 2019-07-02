package com.lucadev.trampoline.security.autoconfigure;

import com.lucadev.trampoline.security.authentication.LastSeenUpdateListener;
import com.lucadev.trampoline.security.configuration.SecurityConfigurationProperties;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.service.time.TimeProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfigure security.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/28/19
 */
@Configuration
@EnableConfigurationProperties(SecurityConfigurationProperties.class)
public class TrampolineSecurityAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public LastSeenUpdateListener lastSeenUpdateListener(UserService userService,
			TimeProvider timeProvider) {
		return new LastSeenUpdateListener(userService, timeProvider);
	}

}
