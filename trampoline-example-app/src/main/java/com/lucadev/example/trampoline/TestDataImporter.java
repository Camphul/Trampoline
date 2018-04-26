package com.lucadev.example.trampoline;

import com.lucadev.trampoline.security.model.Privilege;
import com.lucadev.trampoline.security.model.Role;
import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.repository.UserRepository;
import com.lucadev.trampoline.security.service.PrivilegeService;
import com.lucadev.trampoline.security.service.RoleService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Component
@AllArgsConstructor
public class TestDataImporter implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestDataImporter.class);

    private final PrivilegeService privilegeService;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        LOGGER.info("Running test data imports");
        Role userRole = makeRole("ROLE_USER", "USER_READ", "WHOAMI_GET");
        Role adminRole = makeRole("ROLE_ADMIN", "USER_WRITE", "USER_DELETE");
        makeUser("user", userRole);
        makeUser("admin", userRole, adminRole);
    }

    private void makeUser(String name, Role... roles) {
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
    }

    private Role makeRole(String name, String... privileges) {
        Role role = roleService.create(name);
        for (String privilege : privileges) {
            role.getPrivileges().add(makePrivilege(privilege));
        }
        return roleService.update(role);
    }

    public Privilege makePrivilege(String name) {
        return privilegeService.create(name);
    }
}
