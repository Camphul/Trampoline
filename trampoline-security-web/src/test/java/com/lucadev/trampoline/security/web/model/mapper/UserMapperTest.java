package com.lucadev.trampoline.security.web.model.mapper;

import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.web.model.EmbeddedUser;
import com.lucadev.trampoline.security.web.model.UserDto;
import com.lucadev.trampoline.security.web.model.UserSummaryDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for {@link UserMapper}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@SpringBootTest(classes = { UserMapperImpl.class, RoleMapperImpl.class,
		PrivilegeMapperImpl.class, GrantedAuthorityMapperImpl.class})
@RunWith(SpringRunner.class)
public class UserMapperTest {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private GrantedAuthorityMapper grantedAuthorityMapper;

	@Test
	public void mapperNotNull() {
		assertNotNull(userMapper);
		assertNotNull(roleMapper);
	}

	@Test
	public void toEmbeddedDto() {
		User input = MapperHelper.createUser();
		EmbeddedUser result = userMapper.toEmbeddedDto(input);
		assertEquals(input.getUsername(), result.getUsername());
		assertEquals(input.getEmail(), result.getEmail());
	}

	@Test
	public void toSummary() {
		User input = MapperHelper.createUser();
		UserSummaryDto result = userMapper.toSummary(input);
		assertEquals(input.getUsername(), result.getUsername());
		assertEquals(input.getEmail(), result.getEmail());
		assertEquals(grantedAuthorityMapper.toNames(input.getAuthorities()),
				result.getAuthorities());
	}

	@Test
	public void toDto() {
		User input = MapperHelper.createUser();
		UserDto result = userMapper.toDto(input);
		assertEquals(input.getUsername(), result.getUsername());
		assertEquals(input.getEmail(), result.getEmail());
		assertEquals(input.isEnabled(), result.isEnabled());
		assertEquals(input.isLocked(), result.isLocked());
		assertEquals(input.isExpired(), result.isExpired());
		assertEquals(input.isCredentialsExpired(), result.isCredentialsExpired());
		assertEquals(input.getCreated(), result.getCreated());
		assertEquals(roleMapper.toDtos(input.getRoles()), result.getRoles());
	}

}
