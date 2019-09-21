package com.lucadev.trampoline.security.autoconfigure;

import com.lucadev.trampoline.security.service.UserAuthenticationService;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.security.service.impl.TrampolineUserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Authentication service autoconfig.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass(UserAuthenticationService.class)
public class UserAuthenticationServiceAutoConfiguration {

	private final UserService userService;

	private final PasswordEncoder passwordEncoder;

	@Bean
	@ConditionalOnMissingBean
	public UserAuthenticationService userPasswordService() {
		return new TrampolineUserAuthenticationService(this.userService,
				this.passwordEncoder);
	}

}
