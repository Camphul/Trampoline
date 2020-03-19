package com.lucadev.trampoline.apibrick.autoconfigure;

import com.lucadev.trampoline.apibrick.security.web.handler.AbstractUserCrudControllerHandler;
import com.lucadev.trampoline.apibrick.security.web.handler.UserControllerHandler;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.security.web.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfig
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/19/20
 */
@Configuration
@ConditionalOnClass(User.class)
@RequiredArgsConstructor
@Slf4j
public class SecurityApiBrickAutoConfiguration {

	private final UserMapper userMapper;

	private final UserService userService;

	@Bean
	@ConditionalOnMissingBean
	public AbstractUserCrudControllerHandler userCrudControllerHandler() {
		log.debug("Using default user controller handler.");
		return new UserControllerHandler(this.userService, this.userMapper);
	}

}
