package com.lucadev.trampoline.security.repository;

import com.lucadev.trampoline.data.repository.TrampolineRepository;
import com.lucadev.trampoline.security.model.Role;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Repository
public interface RoleRepository extends TrampolineRepository<Role> {
    Role findOneByName(String name);
}
