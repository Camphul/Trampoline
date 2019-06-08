package com.lucadev.trampoline.security.web.model;

import lombok.Data;

import java.util.List;

/**
 * Role DTO.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Data
public class RoleDto {

	private String name;

	private List<String> privileges;

}
