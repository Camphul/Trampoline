package com.lucadev.trampoline.security.web.model.mapper;

import org.mapstruct.Mapper;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Mapper for {@link org.springframework.security.core.GrantedAuthority} entities.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Mapper
public interface GrantedAuthorityMapper {

	default String toName(GrantedAuthority authority) {
		return authority.getAuthority();
	}

	default Collection<String> toNames(
			Collection<? extends GrantedAuthority> authorities) {
		return authorities.stream().map(this::toName).collect(Collectors.toList());
	}

}
