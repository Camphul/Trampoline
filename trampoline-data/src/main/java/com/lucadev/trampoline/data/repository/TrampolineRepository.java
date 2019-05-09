package com.lucadev.trampoline.data.repository;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * A {@link JpaRepository} specific to {@link TrampolineEntity} entities.
 *
 * @param <T> an entity extending {@link TrampolineEntity}.
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Transactional
@NoRepositoryBean
public interface TrampolineRepository<T extends TrampolineEntity>
		extends JpaRepository<T, UUID> {

}
