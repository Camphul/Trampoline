package com.lucadev.trampoline.security.logging.autoconfig;

import com.lucadev.trampoline.security.logging.handler.UserActivityHandler;
import com.lucadev.trampoline.security.logging.handler.event.UserActivityEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfigures {@link UserActivityHandler} implementation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Configuration
@ConditionalOnClass(UserActivityHandler.class)
@AllArgsConstructor
public class UserActivityHandlerAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public UserActivityHandler userActivityHandler(ApplicationEventPublisher eventPublisher) {
		return new UserActivityEventPublisher(eventPublisher);
	}

}
