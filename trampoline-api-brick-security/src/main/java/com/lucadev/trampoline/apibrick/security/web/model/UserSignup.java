package com.lucadev.trampoline.apibrick.security.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request body used to create users.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/19/20
 */
@Data
@NoArgsConstructor
public class UserSignup {

	private String username;

	private String email;

	private String password;

}
