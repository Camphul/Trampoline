package com.lucadev.trampoline.security.service.impl;

import com.lucadev.trampoline.security.model.Privilege;
import com.lucadev.trampoline.security.repository.PrivilegeRepository;
import com.lucadev.trampoline.security.service.PrivilegeService;
import lombok.AllArgsConstructor;

/**
 * {@link PrivilegeService} implementation.
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@AllArgsConstructor
public class TrampolinePrivilegeService implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Privilege create(String privilege) {
        Privilege p = new Privilege(privilege);
        return privilegeRepository.save(p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(String privilage) {
        privilegeRepository.deleteByName(privilage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Privilege find(String privilegeName) {
        return privilegeRepository.findOneByName(privilegeName);
    }
}
