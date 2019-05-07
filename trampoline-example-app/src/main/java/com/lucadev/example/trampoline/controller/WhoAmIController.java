package com.lucadev.example.trampoline.controller;

import com.lucadev.example.trampoline.service.WhoAmIUserActivityResolver;
import com.lucadev.trampoline.security.CurrentUserNotFoundException;
import com.lucadev.trampoline.security.abac.access.prepost.PrePolicy;
import com.lucadev.trampoline.security.logging.ActivityLayer;
import com.lucadev.trampoline.security.logging.LogUserActivity;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Return the raw user object.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 26-4-18
 */
@RestController
@AllArgsConstructor
public class WhoAmIController {

    private final UserService userService;

    /**
     * Evaluate if principal(current user) has access to this mapping.
     * Then return the current user or throw a {@link CurrentUserNotFoundException}
     *
     * @return whoami dto response.
     */
    @GetMapping("/whoami")
	@PrePolicy("WHO_AM_I")
	@LogUserActivity(value = "whoami", layer = ActivityLayer.CONTROLLER, resolver = WhoAmIUserActivityResolver.class)
    public User whoami() {
        return userService.currentUserOrThrow();
    }

}
