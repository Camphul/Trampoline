package com.lucadev.trampoline.security.autoconfigure;

import com.lucadev.trampoline.security.configuration.SecurityConfigurationProperties;
import com.lucadev.trampoline.security.persistence.repository.UserRepository;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.security.service.impl.TrampolineUserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfigure userservice.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 * @see UserService
 */
@Configuration
@ConditionalOnClass(UserService.class)
public class UserServiceAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public UserService userService(UserRepository repository,
			SecurityConfigurationProperties configurationProperties) {
		return new TrampolineUserService(repository, configurationProperties);
	}

}
