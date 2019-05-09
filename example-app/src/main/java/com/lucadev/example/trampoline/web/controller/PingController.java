package com.lucadev.example.trampoline.web.controller;

import com.lucadev.trampoline.security.abac.access.annotation.PrePolicy;
import com.lucadev.trampoline.security.logging.LogUserActivity;
import com.lucadev.trampoline.service.time.TimeProvider;
import com.lucadev.trampoline.web.model.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple ping controller to return a pong
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@RestController
@RequestMapping("/ping")
@RequiredArgsConstructor
public class PingController {

	private final TimeProvider timeProvider;

	@GetMapping
	@LogUserActivity//Will log a PingController#pong action for the current user.
	@PrePolicy("GET_PING_PROTECTED")
	public MessageResponse pong() {
		return new MessageResponse("Pong at: " + timeProvider.unix());
	}
}
