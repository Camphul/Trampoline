package com.lucadev.trampoline.security.service;

import com.lucadev.trampoline.security.model.Privilege;
import com.lucadev.trampoline.security.model.Role;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public interface RoleService {

    boolean hasRole(String roleName);

    Role findByName(String roleName);

    boolean hasPrivilege(Role role, Privilege privilege);

    void addPrivilege(Role role, Privilege privilege);

    void removePrivilege(Role role, Privilege privilege);

    Role createRole(String roleName);

    Role update(Role role);
}
