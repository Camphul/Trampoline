package com.lucadev.example.trampoline.controller;

import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 26-4-18
 */
@RestController
@AllArgsConstructor
public class WhoamiController {

    private UserService userService;


    @GetMapping("/whoami")
    public User whoami() {
        return userService.currentUser().get();
    }

}
