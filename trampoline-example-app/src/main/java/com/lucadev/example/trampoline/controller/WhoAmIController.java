package com.lucadev.example.trampoline.controller;

import com.lucadev.example.trampoline.service.WhoamiUserActivityResolver;
import com.lucadev.trampoline.security.logging.ActivityLayer;
import com.lucadev.trampoline.security.logging.LogUserActivity;
import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Return the raw user object.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 26-4-18
 */
@RestController
@AllArgsConstructor
public class WhoAmIController {

    private UserService userService;

    /**
     * Evaluate if principal(current user) has access to this mapping.
     * Then return the current user or throw a {@link com.lucadev.trampoline.security.exception.CurrentUserNotFoundException}
     *
     * @return
     */
    @GetMapping("/whoami")
    @PreAuthorize("hasPermission(null, 'WHO_AM_I')")
	@LogUserActivity(value = "whoami", category = "whoami", layer = ActivityLayer.CONTROLLER, resolver = WhoamiUserActivityResolver.class)
    public User whoami() {
        return userService.currentUserOrThrow();
    }

}
