package com.lucadev.example.trampoline.model.dto;

import com.lucadev.trampoline.security.model.User;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

/**
 * Simple {@link User} DTO only giving away the user id and username.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Getter
@ToString
public class UserSummaryDto {

    private final UUID id;
    private final String username;

    public UserSummaryDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }

}
