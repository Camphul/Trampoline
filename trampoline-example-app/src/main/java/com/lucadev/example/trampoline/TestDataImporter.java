package com.lucadev.example.trampoline;

import com.lucadev.example.trampoline.service.BookService;
import com.lucadev.trampoline.security.authentication.SystemAuthentication;
import com.lucadev.trampoline.security.model.Role;
import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.repository.UserRepository;
import com.lucadev.trampoline.security.service.RoleService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Test component to import test data.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Component
@AllArgsConstructor
public class TestDataImporter implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestDataImporter.class);

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookService bookService;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            //to test the builder, ignore this one
            SecurityContext ctx = SecurityContextHolder.createEmptyContext();
            SecurityContextHolder.setContext(ctx);
            ctx.setAuthentication(new SystemAuthentication());

            //Do what ever you want to do
            LOGGER.info("Running test data imports");
            Role userRole = roleService.find("ROLE_USER");
            Role adminRole = roleService.find("ROLE_ADMIN");
            User user = makeUser("user", userRole);
            User admin = makeUser("admin", userRole, adminRole);

            bookService.create("userBook1", user);
            bookService.create("userBook2", user);
            bookService.create("userBook3", user);
            bookService.create("userBook4", user);

            bookService.create("adminBook1", admin);
            bookService.create("adminBook2", admin);
            bookService.create("adminBook3", admin);
            bookService.create("adminBook4", admin);
            bookService.create("adminBook5", admin);
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

    @Override
    public int getOrder() {
        return 90;
    }
}
