package com.lucadev.example.trampoline.web.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * Model used for the WhoAmIController
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@Data
public class UserSummaryDto {

	private UUID id;
	private String username;
	private String email;
	private List<String> roles;
}
