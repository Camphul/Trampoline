package com.lucadev.trampoline.security.web.model.mapper;

import com.lucadev.trampoline.security.persistence.entity.Privilege;
import com.lucadev.trampoline.security.persistence.entity.Role;
import com.lucadev.trampoline.security.persistence.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Helper class for testing mapstruct mappers.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
public class MapperHelper {

	private static final Date TEST_DATE = new Date(300);

	public static List<Role> createRoles() {
		List<Role> roles = new ArrayList<>();
		for (int i = 0; i < 13; i++) {
			roles.add(createRole("ROLE_NUM_" + i));
		}
		return roles;
	}

	public static Role createRole(String name) {
		String[] privs = new String[5];
		for (int i = 0; i < privs.length; i++) {
			privs[i] = "PRIV_" + i;
		}
		return createRole(name, privs);
	}

	public static Role createRole(String name, String... privileges) {
		Role role = new Role();
		role.setName(name);
		for (String privilege : privileges) {
			Privilege priv = new Privilege();
			priv.setName(privilege);
			role.getPrivileges().add(priv);
		}
		return role;
	}

	public static List<User> createUsers() {
		List<User> users = new ArrayList<>();
		for (int i = 0; i < 17; i++) {
			users.add(createUser("johndoe_" + i));
		}
		return users;
	}

	public static User createUser() {
		return createUser("johndoe");
	}

	public static User createUser(String name) {
		User user = new User();
		user.setLastSeen(TEST_DATE);
		user.setLastPasswordReset(TEST_DATE);
		user.setLocked(false);
		user.setEmail(name + "@example.com");
		user.setPassword("blablablabla");
		user.getRoles().addAll(createRoles());
		user.setUsername(name);
		user.setCredentialsExpired(false);
		user.setEnabled(true);
		user.setExpired(false);

		return user;
	}

}
