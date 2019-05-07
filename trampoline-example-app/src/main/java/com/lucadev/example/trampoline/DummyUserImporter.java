package com.lucadev.example.trampoline;

import com.lucadev.trampoline.security.authentication.SystemAuthentication;
import com.lucadev.trampoline.security.persistence.entity.Role;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.persistence.repository.UserRepository;
import com.lucadev.trampoline.security.service.RoleService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Import some dummy users when the application finished loading(ContextRefreshed)
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Component
@AllArgsConstructor
public class DummyUserImporter implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(DummyUserImporter.class);

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Environment environment;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    	if("update".equalsIgnoreCase(environment.getProperty("spring.jpa.hibernate.ddl-auto"))) {
			LOGGER.info("Skipping user imports..");
			return;
		}
        try {
            //Required if you were to use abac on a handler level.
            SecurityContext ctx = SecurityContextHolder.createEmptyContext();
            SecurityContextHolder.setContext(ctx);
            ctx.setAuthentication(new SystemAuthentication());

            //Do what ever you want to do
            LOGGER.info("Running dummy user imports");
            Role userRole = roleService.find("ROLE_USER");
            Role adminRole = roleService.find("ROLE_ADMIN");
            User user = makeUser("user", userRole);
            User user2 = makeUser("jeff", userRole);
            User admin = makeUser("admin", userRole, adminRole);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    private User makeUser(String name, Role... roles) {
        User user = new User();
        user.setUsername(name);
        user.setCredentialsExpired(false);
        user.setEnabled(true);
        user.setExpired(false);
        user.setLastSeen(new Date());
        user.setLastPasswordReset(new Date());
        user.setLocked(false);
        user.setEmail(name + "@example.com");
        user.setPassword(passwordEncoder.encode("test"));
        user = userRepository.save(user);
        for (Role role : roles) {
            user.getRoles().add(role);
        }
        user = userRepository.saveAndFlush(user);
        if (user == null) {
            LOGGER.error("Could not persist user!");
        }
        return user;
    }

    /**
     * Defining load order since Trampoline has an inner listener for ContextRefreshed which is used to configure the authorization scheme.
     *
     * @return loading order.
     */
    @Override
    public int getOrder() {
        return 90;
    }
}
