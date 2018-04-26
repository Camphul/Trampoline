package com.lucadev.trampoline.security.jwt.web.model;

import lombok.*;

import java.io.Serializable;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 14-4-18
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserAuthenticationRequest implements Serializable {

    private String username;
    private String password;

}
