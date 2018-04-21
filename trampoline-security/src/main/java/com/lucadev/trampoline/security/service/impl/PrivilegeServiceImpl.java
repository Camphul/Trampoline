package com.lucadev.trampoline.security.service.impl;

import com.lucadev.trampoline.security.model.Privilege;
import com.lucadev.trampoline.security.repository.PrivilegeRepository;
import com.lucadev.trampoline.security.service.PrivilegeService;
import lombok.AllArgsConstructor;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@AllArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;


    @Override
    public Privilege createPrivilege(String privilege) {
        Privilege p = new Privilege(privilege);
        return privilegeRepository.save(p);
    }

    @Override
    public void removePrivilege(String privilage) {
        privilegeRepository.deleteByName(privilage);
    }

    @Override
    public Privilege findPrivilegeByName(String privilegeName) {
        return privilegeRepository.findOneByName(privilegeName);
    }
}
