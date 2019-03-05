package com.lucadev.trampoline.security.configuration;


import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ConfigurationProperties(prefix = "trampoline.security.authentication")
public class AuthenticationProperties {

    /**
     * If the value trampoline.security.authentication.useEmailIdentification is set to true:
     * The user cannot login using his username but will have to resort using his email instead.
     */
    private boolean emailIdentification = false;

}
