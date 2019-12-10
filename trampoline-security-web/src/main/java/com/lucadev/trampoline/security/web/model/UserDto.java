package com.lucadev.trampoline.security.web.model;

import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * Dto for {@link com.lucadev.trampoline.security.persistence.entity.User}.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Data
public class UserDto {

	private UUID id;

	private String username;

	private String email;

	private Set<RoleDto> roles;

	private Instant created;

	private Instant lastSeen;

	private boolean locked;

	private boolean enabled;

	private boolean expired;

	private boolean credentialsExpired;

}
