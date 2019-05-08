package com.lucadev.trampoline.security.autoconfigure;

import com.lucadev.trampoline.security.service.UserAuthenticationService;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.security.service.impl.TrampolineUserAuthenticationService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@ConditionalOnClass(UserAuthenticationService.class)
public class UserAuthenticationServiceAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public UserAuthenticationService userPasswordService(UserService userService,
			PasswordEncoder passwordEncoder) {
		return new TrampolineUserAuthenticationService(userService, passwordEncoder);
	}

}
