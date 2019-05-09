package com.lucadev.example.trampoline.web.model.mapper;

import com.lucadev.example.trampoline.web.model.UserSummaryDto;
import com.lucadev.trampoline.security.persistence.entity.User;
import org.mapstruct.Mapper;


/**
 * MapStruct mapper for User objects.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

	/**
	 * Create summary dto.
	 * @param user trampoline user
	 * @return user summary dto.
	 */
	UserSummaryDto toSummaryDto(User user);

}
