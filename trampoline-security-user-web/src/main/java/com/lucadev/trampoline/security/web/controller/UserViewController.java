package com.lucadev.trampoline.security.web.controller;

import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.security.web.annotation.UserRestController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

/**
 * View specific {@link User}
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@UserRestController
@AllArgsConstructor
public class UserViewController {

    private final UserService userService;

    @GetMapping("/{uuid}")
    public User getUser(@PathVariable UUID uuid) {
        return userService.findById(uuid);
    }

}
