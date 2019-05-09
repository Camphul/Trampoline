package com.lucadev.example.trampoline.web.model.mapper;

import com.lucadev.trampoline.security.persistence.entity.Role;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for trampoline roles
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@Mapper(componentModel = "spring")
public interface RoleMapper {

	/**
	 * Convert role to name string.
	 *
	 * @param role role to map.
	 * @return role name.
	 */
	default String toRoleName(Role role) {
		if (role == null) {
			return "";
		}
		return role.getName();
	}

	/**
	 * Map list of roles.
	 *
	 * @param roles list of roles.
	 * @return mapped list.
	 */
	default List<String> toRoleNames(List<Role> roles) {
		if (roles == null) {
			return new ArrayList<>();
		}
		return roles.stream().map(Role::getName).collect(Collectors.toList());
	}
}
