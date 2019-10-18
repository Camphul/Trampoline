package com.lucadev.trampoline.security.jwt.adapter.security.web.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Request which contains credentials to authorize a user.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 14-4-18
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserAuthenticationRequest implements Serializable {

	@NotNull
	@Size(min = 2, max = 256)
	@NotBlank
	private String identifier;

	@NotNull
	@NotBlank
	@Size(min = 2, max = 256)
	private String password;

}
