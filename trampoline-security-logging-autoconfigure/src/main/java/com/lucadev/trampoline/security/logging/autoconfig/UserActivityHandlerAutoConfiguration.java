package com.lucadev.trampoline.security.logging.autoconfig;

import com.lucadev.trampoline.security.logging.activity.handler.LogUserActivityHandler;
import com.lucadev.trampoline.security.logging.activity.handler.UserActivityHandler;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfigures {@link UserActivityHandler} implementation.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Configuration
@ConditionalOnClass(UserActivityHandler.class)
@AllArgsConstructor
public class UserActivityHandlerAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public UserActivityHandler userActivityHandler() {
		return new LogUserActivityHandler();
	}
}
