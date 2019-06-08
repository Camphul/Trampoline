package com.lucadev.trampoline.security.web.model.mapper;

import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.web.model.EmbeddedUser;
import com.lucadev.trampoline.security.web.model.UserSummaryDto;
import org.mapstruct.Mapper;

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
	EmbeddedUser toEmbedded(User user);

	/**
	 * Map to summary.
	 * @param user user to map.
	 * @return user summary.
	 */
	UserSummaryDto toSummary(User user);

}
