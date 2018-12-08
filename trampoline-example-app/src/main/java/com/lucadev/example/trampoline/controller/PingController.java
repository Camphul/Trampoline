package com.lucadev.example.trampoline.controller;

import com.lucadev.trampoline.model.MessageResponse;
import com.lucadev.trampoline.service.time.TimeProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ping controller
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@RestController
@AllArgsConstructor
public class PingController {

    private final TimeProvider timeProvider;

    /**
     * Unprotected ping route since it is ignored through the configuration we set.
     *
     * @return
     */
    @GetMapping("/ping/unprotected")
    public MessageResponse pingUnprotected() {
        return new MessageResponse("Pong unprotected: " + timeProvider.unix());
    }

    /**
     * Protected ping which evaluates a policy on the current principal.
     *
     * @return
     */
    @GetMapping("/ping/protected")
    @PreAuthorize("hasPermission(null, 'PING_PROTECTED')")
    public MessageResponse pingProtected() {
        return new MessageResponse("Pong protected: " + timeProvider.unix());
    }

}
