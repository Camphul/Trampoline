package com.lucadev.trampoline.data;

import com.lucadev.trampoline.data.repository.TrampolineRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Repository
public interface SimpleTrampolineEntityRepository
		extends TrampolineRepository<SimpleTrampolineEntity> {

	SimpleTrampolineEntity findOneByPayload(String payload);

}
