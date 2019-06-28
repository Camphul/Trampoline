package com.lucadev.trampoline.security.web.model;

import lombok.Data;

import java.util.UUID;

/**
 * Model for {@link com.lucadev.trampoline.security.persistence.entity.User} to embed into
 * another model.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Data
public class EmbeddedUser {

	private UUID id;

	private String username;

	private String email;

}
