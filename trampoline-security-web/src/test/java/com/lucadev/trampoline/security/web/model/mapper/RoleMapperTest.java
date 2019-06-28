package com.lucadev.trampoline.security.web.model.mapper;

import com.lucadev.trampoline.security.persistence.entity.Role;
import com.lucadev.trampoline.security.web.model.RoleDto;
import com.lucadev.trampoline.security.web.model.RoleSummaryDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for {@link RoleMapper}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@SpringBootTest(classes = { RoleMapperImpl.class, PrivilegeMapperImpl.class })
@RunWith(SpringRunner.class)
public class RoleMapperTest {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private PrivilegeMapper privilegeMapper;

	@Test
	public void mapperNotNull() {
		assertNotNull(roleMapper);
		assertNotNull(privilegeMapper);
	}

	@Test
	public void toName() {
		String expected = "ROLE_USER";
		Role role = MapperHelper.createRole(expected);
		String result = roleMapper.toName(role);
		assertEquals(expected, result);
	}

	@Test
	public void toNames() {
		List<Role> input = MapperHelper.createRoles();

		List<String> expected = input.stream().map(Role::getName)
				.collect(Collectors.toList());
		List<String> result = roleMapper.toNames(input);
		assertEquals(expected, result);
	}

	@Test
	public void toSummary() {
		String expected = "ROLE_ADMIN";
		Role role = MapperHelper.createRole(expected);
		RoleSummaryDto result = roleMapper.toSummary(role);
		assertEquals(expected, result.getName());
	}

	@Test
	public void toSummaries() {
		List<Role> input = MapperHelper.createRoles();

		List<RoleSummaryDto> result = roleMapper.toSummaries(input);

		List<String> mappedNamesExpectation = input.stream().map(Role::getName)
				.collect(Collectors.toList());
		List<String> mappedNamesResult = result.stream().map(RoleSummaryDto::getName)
				.collect(Collectors.toList());
		assertEquals(mappedNamesExpectation, mappedNamesResult);
	}

	@Test
	public void toDto_Name() {
		String expectedName = "ROLE_MODERATOR";
		Role role = MapperHelper.createRole(expectedName);
		RoleDto result = roleMapper.toDto(role);
		assertEquals(expectedName, result.getName());
	}

	@Test
	public void toDtos_Name() {
		List<Role> roles = MapperHelper.createRoles();
		List<String> mappedNamesExpectation = roles.stream().map(Role::getName)
				.collect(Collectors.toList());
		List<RoleDto> dtoResult = roleMapper.toDtos(roles);
		List<String> mappedResultNames = dtoResult.stream().map(RoleDto::getName)
				.collect(Collectors.toList());
		assertEquals(mappedNamesExpectation, mappedResultNames);
	}

	@Test
	public void toDto_Privileges() {
		Role role = MapperHelper.createRole("ROLE_DEVELOPER");
		List<String> expectedPrivileges = privilegeMapper.toNames(role.getPrivileges());
		RoleDto result = roleMapper.toDto(role);
		assertEquals(expectedPrivileges, result.getPrivileges());
	}

	@Test
	public void toDtos_Privileges() {
		List<Role> roles = MapperHelper.createRoles();
		List<RoleDto> dtoResult = roleMapper.toDtos(roles);

		for (int i = 0; i < roles.size(); i++) {
			Role role = roles.get(i);
			RoleDto result = dtoResult.get(i);
			List<String> expected = privilegeMapper.toNames(role.getPrivileges());
			assertEquals(expected, result.getPrivileges());
		}
	}

}