package com.lucadev.trampoline.security.service;

import com.lucadev.trampoline.security.model.Privilege;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public interface PrivilegeService {

    Privilege createPrivilege(String privilege);

    void removePrivilege(String privilage);

    Privilege findPrivilegeByName(String privilegeName);
}
