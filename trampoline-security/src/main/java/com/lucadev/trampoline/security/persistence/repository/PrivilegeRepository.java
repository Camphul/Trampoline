package com.lucadev.trampoline.security.persistence.repository;

import com.lucadev.trampoline.data.repository.TrampolineRepository;
import com.lucadev.trampoline.security.persistence.entity.Privilege;
import org.springframework.stereotype.Repository;

/**
 * {@link TrampolineRepository} for {@link Privilege} entities.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Repository
public interface PrivilegeRepository extends TrampolineRepository<Privilege> {
	void deleteByName(String privilage);

	Privilege findOneByName(String privilegeName);
}
