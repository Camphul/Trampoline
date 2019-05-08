package com.lucadev.example.trampoline.controller;

import com.lucadev.trampoline.web.model.MessageResponse;
import com.lucadev.trampoline.security.abac.access.annotation.PrePolicy;
import com.lucadev.trampoline.security.logging.ActivityLayer;
import com.lucadev.trampoline.security.logging.LogUserActivity;
import com.lucadev.trampoline.service.time.TimeProvider;
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
	@LogUserActivity(value = "ping_unprotected", category = "ping", layer = ActivityLayer.CONTROLLER)
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
	@LogUserActivity(value = "ping_protected", category = "ping", layer = ActivityLayer.CONTROLLER)
    public MessageResponse pingProtected() {
        return new MessageResponse("Pong protected: " + timeProvider.unix());
    }

}
