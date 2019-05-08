package com.lucadev.example.trampoline.controller;

import com.lucadev.trampoline.security.abac.access.annotation.PrePolicy;
import com.lucadev.trampoline.service.time.TimeProvider;
import com.lucadev.trampoline.web.model.MessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ping model
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7-12-18
 */
@RestController
@AllArgsConstructor
public class PingController {

	private final TimeProvider timeProvider;

	/**
	 * Unprotected ping route since it is ignored through the configuration we set.
	 *
	 * @return message response.
	 */
	@GetMapping("/ping/unprotected")
	public MessageResponse pingUnprotected() {
		return new MessageResponse("Pong unprotected: " + timeProvider.unix());
	}

	/**
	 * Protected ping which evaluates a policy on the current principal.
	 *
	 * @return message response.
	 */
	@GetMapping("/ping/protected")
	@PrePolicy("PING_PROTECTED")
	public MessageResponse pingProtected() {
		return new MessageResponse("Pong protected: " + timeProvider.unix());
	}

}
