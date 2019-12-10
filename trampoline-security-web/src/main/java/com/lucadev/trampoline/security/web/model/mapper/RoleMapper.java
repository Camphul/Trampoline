package com.lucadev.trampoline.security.web.model.mapper;

import com.lucadev.trampoline.security.persistence.entity.Role;
import com.lucadev.trampoline.security.web.configuration.WebSecurityMapperConfiguration;
import com.lucadev.trampoline.security.web.model.RoleDto;
import com.lucadev.trampoline.security.web.model.RoleSummaryDto;
import org.mapstruct.Mapper;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * MapStruct mapper for {@link Role}.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Mapper(config = WebSecurityMapperConfiguration.class, uses = PrivilegeMapper.class)
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
	 *
	 * @param roles roles to map.
	 * @return mapped role names.
	 */
	default Set<String> toNames(Set<Role> roles) {
		return roles.stream().map(this::toName).collect(Collectors.toSet());
	}

	/**
	 * Map to summary.
	 * @param role role to map.
	 * @return a {@link RoleSummaryDto}.
	 */
	RoleSummaryDto toSummary(Role role);

	/**
	 * Map roles to summary dto's.
	 *
	 * @param roles roles to map.
	 * @return summary dto's.
	 */
	Set<RoleSummaryDto> toSummaries(Set<Role> roles);

	/**
	 * Map role to dto.
	 * @param role role to map.
	 * @return dto.
	 */
	RoleDto toDto(Role role);

	/**
	 * Map roles to dto's.
	 *
	 * @param roles roles to map.
	 * @return dto's.
	 */
	Set<RoleDto> toDtos(Set<Role> roles);

}
