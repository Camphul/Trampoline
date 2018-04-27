package com.lucadev.trampoline.security.web.controller;

import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.security.web.annotation.UserRestController;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Controller for paginated user view.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@UserRestController
@AllArgsConstructor
public class UserPageController {

    private final UserService userService;

    @GetMapping
    public Page<User> getUsers(Pageable pageable) {
        return userService.findAll(pageable);
    }

}
