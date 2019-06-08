package com.lucadev.trampoline.security.web.model.mapper;

import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.web.model.EmbeddedUser;
import com.lucadev.trampoline.security.web.model.UserDto;
import com.lucadev.trampoline.security.web.model.UserSummaryDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * MapStruct mapper for {@link User}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Mapper(uses = RoleMapper.class)
public interface UserMapper {

	/**
	 * To basic embeddable user.
	 * @param user user to map.
	 * @return embeddable model.
	 */
	EmbeddedUser toEmbeddedDto(User user);

	/**
	 * To basic embeddable user.
	 * @param user user to map.
	 * @return embeddable model.
	 */
	List<EmbeddedUser> toEmbeddedDtos(List<User> user);

	/**
	 * Map to summary.
	 * @param user user to map.
	 * @return a {@link UserSummaryDto}.
	 */
	UserSummaryDto toSummary(User user);

	/**
	 * Map users to summary dto's.
	 * @param users users to map.
	 * @return summary dto's.
	 */
	List<UserSummaryDto> toSummaries(List<User> users);

	/**
	 * Map user to dto.
	 * @param user user to map.
	 * @return dto.
	 */
	UserDto toDto(User user);

	/**
	 * Map users to dto's.
	 * @param users users to map.
	 * @return dto's.
	 */
	List<UserDto> toDtos(List<User> users);

}
