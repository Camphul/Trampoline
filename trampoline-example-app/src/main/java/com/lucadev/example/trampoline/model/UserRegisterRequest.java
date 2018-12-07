package com.lucadev.example.trampoline.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserRegisterRequest {

    private String username;
    private String email;
    private String password;

}
