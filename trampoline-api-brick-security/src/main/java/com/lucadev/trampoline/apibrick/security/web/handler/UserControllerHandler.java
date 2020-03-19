package com.lucadev.trampoline.apibrick.security.web.handler;

import com.lucadev.trampoline.apibrick.security.web.model.UserSignup;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.security.web.model.UserDto;
import com.lucadev.trampoline.security.web.model.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Default user controller handler.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/19/20
 */
public class UserControllerHandler extends AbstractUserCrudControllerHandler {

	public UserControllerHandler(UserService service, UserMapper userMapper) {
		super(service, userMapper);
	}

	@Override
	public ResponseEntity<UserDto> create(UserSignup model) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}

}
