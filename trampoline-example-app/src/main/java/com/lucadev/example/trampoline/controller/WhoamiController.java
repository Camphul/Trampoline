package com.lucadev.example.trampoline.controller;

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
public class WhoamiController {

    private UserService userService;

    @GetMapping("/whoami")
    @PreAuthorize("hasPermission(null, 'WHO_AM_I')")
    public User whoami() {
        return userService.currentUser().get();
    }

}
