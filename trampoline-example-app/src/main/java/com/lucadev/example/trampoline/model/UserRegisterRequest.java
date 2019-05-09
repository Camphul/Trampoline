package com.lucadev.example.trampoline.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Request to register a user.
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7-12-18
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserRegisterRequest {

	@NotBlank
	@NotNull
	@Size(min = 2, max = 32)
	private String username;
	@Email
	private String email;
	@NotBlank
	@NotNull
	@Size(min = 2, max = 256)
	private String password;

}
