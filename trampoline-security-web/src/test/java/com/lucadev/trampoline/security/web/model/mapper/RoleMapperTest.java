package com.lucadev.trampoline.security.web.model.mapper;

import com.lucadev.trampoline.security.persistence.entity.Role;
import com.lucadev.trampoline.security.web.model.RoleDto;
import com.lucadev.trampoline.security.web.model.RoleSummaryDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.Set;
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
		Set<Role> input = MapperHelper.createRoles();

		Set<String> expected = input.stream().map(Role::getName)
				.collect(Collectors.toSet());
		Set<String> result = roleMapper.toNames(input);
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
		Set<Role> input = MapperHelper.createRoles();

		Set<RoleSummaryDto> result = roleMapper.toSummaries(input);

		Set<String> mappedNamesExpectation = input.stream().map(Role::getName)
				.collect(Collectors.toSet());
		Set<String> mappedNamesResult = result.stream().map(RoleSummaryDto::getName)
				.collect(Collectors.toSet());
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
		Set<Role> roles = MapperHelper.createRoles();
		Set<String> mappedNamesExpectation = roles.stream().map(Role::getName)
				.collect(Collectors.toSet());
		Set<RoleDto> dtoResult = roleMapper.toDtos(roles);
		Set<String> mappedResultNames = dtoResult.stream().map(RoleDto::getName)
				.collect(Collectors.toSet());
		assertEquals(mappedNamesExpectation, mappedResultNames);
	}

	@Test
	public void toDto_Privileges() {
		Role role = MapperHelper.createRole("ROLE_DEVELOPER");
		Set<String> expectedPrivileges = privilegeMapper.toNames(role.getPrivileges());
		RoleDto result = roleMapper.toDto(role);
		assertEquals(expectedPrivileges, result.getPrivileges());
	}

	@Test
	public void toDtos_Privileges() {
		Set<Role> roles = MapperHelper.createRoles();
		Set<RoleDto> dtoResult = roleMapper.toDtos(roles);

		Iterator<Role> it1 = roles.iterator();
		Iterator<RoleDto> it2 = dtoResult.iterator();
		while (it1.hasNext() && it2.hasNext()) {
			Role role = it1.next();
			RoleDto result = it2.next();
			Set<String> expected = privilegeMapper.toNames(role.getPrivileges());
			assertEquals(expected, result.getPrivileges());
		}
	}

}
