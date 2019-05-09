package com.lucadev.example.trampoline;

import com.lucadev.trampoline.security.persistence.entity.Role;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.service.RoleService;
import com.lucadev.trampoline.security.service.UserAuthenticationService;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.service.time.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Component used to import dummy users to test around with.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix="spring.jpa.hibernate", name="ddl-auto", havingValue = "create")//Only run when db scheme is dropped at start
public class DummyUserImporter implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DummyUserImporter.class);
	/**
	 * Dummy user's email domain
	 */
	private static final String USER_EMAIL_DOMAIN = "example.com";
	/**
	 * Dummy user password
	 */
	private static final String USER_PASSWORD = "test";

	private final TimeProvider timeProvider;
	private final UserService userService;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		LOGGER.info("Running dummy user imports..");
		Role userRole = roleService.find("ROLE_USER");
		Role adminRole = roleService.find("ROLE_ADMIN");
		User user = createUser("user", userRole);
		User joe = createUser("joe", userRole);
		User admin = createUser("admin", userRole, adminRole);
		LOGGER.info("Completed dummy user imports");
	}

	private User createUser(String name, Role... roles) {
		User user = new User();
		user.setUsername(name);
		user.setCredentialsExpired(false);
		user.setEnabled(true);
		user.setExpired(false);
		user.setLastSeen(timeProvider.now());
		user.setLastPasswordReset(timeProvider.now());
		user.setLocked(false);
		user.setEmail(name + "@" + USER_EMAIL_DOMAIN);
		user.setPassword(passwordEncoder.encode(USER_PASSWORD));
		//user = userService.save(user);
		for (Role role : roles) {
			user.getRoles().add(role);
		}
		user = userService.save(user);
		if (user == null) {
			LOGGER.error("Could not persist user!");
		} else {
			LOGGER.info("Created new user with id {}", user.getId());
		}
		return user;
	}
}
