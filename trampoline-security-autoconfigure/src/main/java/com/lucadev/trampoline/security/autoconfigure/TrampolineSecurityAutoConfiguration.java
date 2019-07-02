package com.lucadev.trampoline.security.autoconfigure;

import com.lucadev.trampoline.security.authentication.LastSeenUpdateHandler;
import com.lucadev.trampoline.security.configuration.SecurityConfigurationProperties;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.service.time.TimeProvider;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@EnableConfigurationProperties(SecurityConfigurationProperties.class)
public class TrampolineSecurityAutoConfiguration {

	private final UserService userService;
	private final TimeProvider timeProvider;

	@Bean
	@ConditionalOnMissingBean
	public LastSeenUpdateHandler lastSeenUpdateHandler() {
		return new LastSeenUpdateHandler(userService, timeProvider);
	}

}
