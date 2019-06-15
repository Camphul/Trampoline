package com.lucadev.example.trampoline;

import com.lucadev.trampoline.security.persistence.entity.Role;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.service.RoleService;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.service.time.TimeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Component used to import dummy users to test around with.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@Slf4j
@RequiredArgsConstructor
public class DummyUserImporter implements ApplicationListener<ContextRefreshedEvent> {

	/**
	 * Dummy user's email domain.
	 */
	private static final String USER_EMAIL_DOMAIN = "example.com";

	/**
	 * Dummy user password.
	 */
	private static final String USER_PASSWORD = "test";

	private final TimeProvider timeProvider;

	private final UserService userService;

	private final RoleService roleService;

	private final PasswordEncoder passwordEncoder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		log.info("Running dummy user imports..");
		Role userRole = this.roleService.find("ROLE_USER");
		Role adminRole = this.roleService.find("ROLE_ADMIN");
		User user = createUser("user", userRole);
		User joe = createUser("joe", userRole);
		User admin = createUser("admin", userRole, adminRole);
		log.info("Completed dummy user imports");
	}

	private User createUser(String name, Role... roles) {
		User user = new User();
		user.setUsername(name);
		user.setCredentialsExpired(false);
		user.setEnabled(true);
		user.setExpired(false);
		user.setLastSeen(this.timeProvider.now());
		user.setLastPasswordReset(this.timeProvider.now());
		user.setLocked(false);
		user.setEmail(name + "@" + USER_EMAIL_DOMAIN);
		user.setPassword(this.passwordEncoder.encode(USER_PASSWORD));
		// user = userService.save(user);
		for (Role role : roles) {
			user.getRoles().add(role);
		}
		user = this.userService.save(user);
		if (user == null) {
			log.error("Could not persist user!");
		}
		else {
			log.info("Created new user with id {}", user.getId());
		}
		return user;
	}

}
