package com.lucadev.example.trampoline.controller;

import com.lucadev.example.trampoline.model.UserRegisterRequest;
import com.lucadev.trampoline.security.persistence.entity.Role;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.service.RoleService;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.web.model.UUIDDto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * Register a user. Nothing special here.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7-12-18
 */
@RestController
@AllArgsConstructor
public class UserRegistrationController {

	private UserService userService;
	private PasswordEncoder passwordEncoder;
	private RoleService roleService;

	/**
	 * Should be done through the use of a dedicated handler but meh.
	 *
	 * @param signupRequest signup request dto.
	 * @return response.
	 */
	@PostMapping("/signup")
	public UUIDDto signup(@RequestBody @Valid UserRegisterRequest signupRequest) {
		String username = signupRequest.getUsername();
		String email = signupRequest.getEmail();
		//Passwords must be hashed.
		String hashedPassword = passwordEncoder.encode(signupRequest.getPassword());

		User user = new User();
		user.setUsername(username);
		user.setCredentialsExpired(false);
		user.setEnabled(true);
		user.setExpired(false);
		user.setLastSeen(new Date());
		user.setLastPasswordReset(new Date());
		user.setLocked(false);
		user.setEmail(email);
		user.setPassword(hashedPassword);

		user = userService.save(user);

		//Add default role to user
		Role role = roleService.find("ROLE_USER");
		user.getRoles().add(role);

		user = userService.update(user);

		return new UUIDDto(user.getId());
	}

}
