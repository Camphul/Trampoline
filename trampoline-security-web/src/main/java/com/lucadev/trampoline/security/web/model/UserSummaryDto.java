package com.lucadev.trampoline.security.web.model;

import lombok.Data;

import java.util.Collection;
import java.util.UUID;

/**
 * Model for {@link com.lucadev.trampoline.security.persistence.entity.User}.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Data
public class UserSummaryDto {

	private UUID id;

	private String username;

	private String email;

	private Collection<String> authorities;

}
