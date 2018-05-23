package com.lucadev.trampoline.security.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucadev.trampoline.security.model.Privilege;
import com.lucadev.trampoline.security.model.Role;
import com.lucadev.trampoline.security.model.auth.scheme.AuthorizationSchemeModel;
import com.lucadev.trampoline.security.service.AuthorizationSchemeService;
import com.lucadev.trampoline.security.service.PrivilegeService;
import com.lucadev.trampoline.security.service.RoleService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@AllArgsConstructor
public class JsonAuthorizationSchemeService implements AuthorizationSchemeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonAuthorizationSchemeService.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final Environment environment;
    private final RoleService roleService;
    private final PrivilegeService privilegeService;

    /**
     * Import roles
     *
     * @param model        entire model
     * @param privilegeMap mapping of loaded privileges
     */
    private void importPrivileges(AuthorizationSchemeModel model, Map<String, UUID> privilegeMap) {
        for (AuthorizationSchemeModel.PrivilegeAuthorizationSchemeModel privilegeModel : model.getPrivileges()) {
            Privilege privilege = privilegeService.create(privilegeModel.getName());
            privilegeMap.put(privilegeModel.getName(), privilege.getId());
            LOGGER.info("JSON IMPORTED PRIVILEGE::{}", privilege);
        }
    }

    /**
     * Import roles
     *
     * @param model
     * @param roleMap
     */
    private void importRoles(AuthorizationSchemeModel model, Map<String, UUID> roleMap) {
        for (AuthorizationSchemeModel.RoleAuthorizationSchemeModel roleModel : model.getRoles()) {
            //create
            Role role = roleService.create(roleModel.getName());
            for (String privilegeName : roleModel.getPrivileges()) {
                Privilege privilege = privilegeService.find(privilegeName);
                role.getPrivileges().add(privilege);
            }
            role = roleService.update(role);
            roleMap.put(roleModel.getName(), role.getId());


            LOGGER.info("JSON IMPORTED ROLE::{}", role);
        }
    }

    private boolean containsValidProfile(AuthorizationSchemeModel model) {
        for (String prof : environment.getActiveProfiles()) {
            for (String modelProfile : model.getProfiles()) {
                if (prof.equals(modelProfile)) {
                    return true;
                }
            }
        }
        LOGGER.info("Could not find matching profile. Not importing scheme");
        return false;
    }

    @Override
    public AuthorizationSchemeModel loadModel(Resource resource) throws IOException {
        File file = resource.getFile();
        if (!file.exists()) {
            throw new FileNotFoundException("Cannot load non existent resource");
        }
        LOGGER.info("Starting import of authorization scheme");
        return MAPPER.readValue(file, AuthorizationSchemeModel.class);
    }

    @Override
    @Transactional
    public void importAuthorizationScheme(AuthorizationSchemeModel model) throws IOException {
        if (!containsValidProfile(model)) {
            return;
        }
        Map<String, UUID> roleMap = new HashMap<>();
        Map<String, UUID> privilegeMap = new HashMap<>();
        importPrivileges(model, privilegeMap);
        importRoles(model, roleMap);
    }
}
