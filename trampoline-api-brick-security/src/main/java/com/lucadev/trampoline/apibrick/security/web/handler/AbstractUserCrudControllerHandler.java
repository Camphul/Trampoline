package com.lucadev.trampoline.apibrick.security.web.handler;

import com.lucadev.trampoline.apibrick.security.web.model.UserSignup;
import com.lucadev.trampoline.apibrick.web.handler.AbstractCrudControllerHandler;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.security.web.model.UserDto;
import com.lucadev.trampoline.security.web.model.UserSummaryDto;
import com.lucadev.trampoline.security.web.model.mapper.UserMapper;

/**
 * Controller handler.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/19/20
 */
public abstract class AbstractUserCrudControllerHandler extends
		AbstractCrudControllerHandler<User, UserService, UserDto, UserSummaryDto, UserSignup> {

	protected AbstractUserCrudControllerHandler(UserService service,
												UserMapper userMapper) {
		super(service, userMapper);
	}

}
