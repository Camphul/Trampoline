package com.lucadev.trampoline.security.web.model.mapper;

import com.lucadev.trampoline.security.persistence.entity.Privilege;
import com.lucadev.trampoline.security.web.model.PrivilegeDto;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for {@link Privilege} entities.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Mapper
public interface PrivilegeMapper {

	/**
	 * Map privilege to name.
	 * @param privilege privilege to map.
	 * @return name of privilege.
	 */
	default String toName(Privilege privilege) {
		return privilege.getName();
	}

	/**
	 * Map privileges to their names.
	 * @param privileges the privileges to map.
	 * @return names of the privileges.
	 */
	default List<String> toNames(Collection<Privilege> privileges) {
		return privileges.stream().map(this::toName).collect(Collectors.toList());
	}

	/**
	 * Map privilege to dto.
	 * @param privilege privilege to map.
	 * @return dto.
	 */
	PrivilegeDto toDto(Privilege privilege);

	/**
	 * Map privileges to dto's.
	 * @param privileges privileges to map.
	 * @return dto's.
	 */
	List<PrivilegeDto> toDtos(Collection<Privilege> privileges);

}
