package com.lucadev.trampoline.security.persistence.repository;

import com.lucadev.trampoline.data.repository.TrampolineRepository;
import com.lucadev.trampoline.security.persistence.entity.Role;
import org.springframework.stereotype.Repository;

/**
 * {@link TrampolineRepository} for {@link Role} entities.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Repository
public interface RoleRepository extends TrampolineRepository<Role> {
	Role findOneByName(String name);
}
