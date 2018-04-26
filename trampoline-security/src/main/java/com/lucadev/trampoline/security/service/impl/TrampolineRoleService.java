package com.lucadev.trampoline.security.service.impl;

import com.lucadev.trampoline.security.model.Privilege;
import com.lucadev.trampoline.security.model.Role;
import com.lucadev.trampoline.security.repository.RoleRepository;
import com.lucadev.trampoline.security.service.RoleService;

/**
 * {@link RoleService} implementation
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class TrampolineRoleService implements RoleService {

    private final RoleRepository roleRepository;

    public TrampolineRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasRole(String roleName) {
        return findByName(roleName) != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role findByName(String roleName) {
        if (roleName == null || roleName.isEmpty()) {
            return null;
        }
        return roleRepository.findOneByName(roleName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPrivilege(Role role, Privilege privilege) {
        if (role == null || privilege == null) {
            return false;
        }
        return role.getPrivileges().stream().anyMatch(p -> p.getName().equals(privilege.getName()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPrivilege(Role role, Privilege privilege) {
        role.getPrivileges().add(privilege);
        roleRepository.save(role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePrivilege(Role role, Privilege privilege) {
        role.getPrivileges().remove(privilege);
        roleRepository.save(role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role createRole(String roleName) {
        Role role = new Role(roleName);
        return roleRepository.save(role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role update(Role role) {
        return roleRepository.save(role);
    }
}
