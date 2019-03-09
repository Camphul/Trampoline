package com.lucadev.trampoline.security.jwt.web.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

	@NotNull(message = "Identifier may not be null.")
	@Size(min=2, max=256, message = "Identifier must be between 2 and 256 characters.")
	@NotBlank(message = "Identifier may not be blank.")
    private String identifier;

	@NotNull(message = "Password may not be null.")
	@NotBlank(message = "Password may not be blank.")
	@Size(min=2, max=256, message = "Password must be between 2 and 256 characters.")
    private String password;

}
