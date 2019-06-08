package com.lucadev.trampoline.security.web.model.mapper;

import com.lucadev.trampoline.security.persistence.entity.Role;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Mapper
public interface RoleMapper {

	/**
	 * Map to role name.
	 * @param role role to map.
	 * @return role name.
	 */
	default String toName(Role role) {
		return role.getName();
	}

	/**
	 * Map role names.
	 * @param roles roles to map.
	 * @return mapped role names.
	 */
	default List<String> toNames(List<Role> roles) {
		return roles.stream().map(this::toName).collect(Collectors.toList());
	}

}
