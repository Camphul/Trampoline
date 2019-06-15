package com.lucadev.example.trampoline;

import com.lucadev.trampoline.EnableTrampoline;
import com.lucadev.trampoline.security.logging.EnableUserActivityLogging;
import com.lucadev.trampoline.security.service.RoleService;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.service.time.TimeProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Main class for example application.
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 */
@SpringBootApplication
@EnableTrampoline
@EnableUserActivityLogging
public class ExampleAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleAppApplication.class, args);
	}

	@Bean
	@ConditionalOnProperty(prefix = "spring.jpa.hibernate", name = "ddl-auto",
			havingValue = "create") // Only run when db scheme is dropped at start
	public DummyUserImporter dummyUserImporter(TimeProvider timeProvider,
			UserService userService, RoleService roleService,
			PasswordEncoder passwordEncoder) {
		return new DummyUserImporter(timeProvider, userService, roleService,
				passwordEncoder);
	}

}
