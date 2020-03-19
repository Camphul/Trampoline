package com.lucadev.trampoline.apibrick.security.web.controller;

import com.lucadev.trampoline.apibrick.security.web.handler.AbstractUserCrudControllerHandler;
import com.lucadev.trampoline.apibrick.security.web.model.UserSignup;
import com.lucadev.trampoline.apibrick.web.controller.AbstractCrudController;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.security.web.model.UserDto;
import com.lucadev.trampoline.security.web.model.UserSummaryDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User crud controller.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/19/20
 */
@RestController
@ConditionalOnBean(AbstractUserCrudControllerHandler.class)
@RequestMapping("/users")
public class UserCrudController extends
		AbstractCrudController<User, UserService, AbstractUserCrudControllerHandler, UserDto, UserSummaryDto, UserSignup> {

	/**
	 * Initialize user crud controller.
	 *
	 * @param handler controller handler.
	 */
	public UserCrudController(AbstractUserCrudControllerHandler handler) {
		super(handler);
	}

}
