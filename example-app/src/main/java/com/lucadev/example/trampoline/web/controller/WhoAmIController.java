package com.lucadev.example.trampoline.web.controller;

import com.lucadev.trampoline.notify.email.EmailService;
import com.lucadev.trampoline.security.abac.access.annotation.PrePolicy;
import com.lucadev.trampoline.security.logging.LogUserActivity;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.security.web.model.UserSummaryDto;
import com.lucadev.trampoline.security.web.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;


/**
 * WhoAmI controller to get the current user details.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@RequiredArgsConstructor
@RestController
public class WhoAmIController {

	private final UserService userService;

	private final UserMapper userMapper;

	private final EmailService emailService;


	@GetMapping("/whoami")
	@LogUserActivity("GET_WHO_AM_I") // Log action as GET_WHO_AM_I
	@PrePolicy("GET_WHO_AM_I") // Check if we are allowed to access this endpoint before
								// invocation
	public UserSummaryDto whoAmI() throws MessagingException {
		emailService.sendAsync(builder -> builder.to("luca@camphuisen.com").withSubject("WhoAmI Accessed"));
		// Get current user or throw an exception(since we cant recover from null)
		User currentUser = userService.currentUserOrThrow();
		return userMapper.toSummary(currentUser);
	}

}
