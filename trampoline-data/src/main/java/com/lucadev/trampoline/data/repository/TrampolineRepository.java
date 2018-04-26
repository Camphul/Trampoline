package com.lucadev.trampoline.data.repository;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

/**
 * A {@link JpaRepository} specific to {@link TrampolineEntity} entities.
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@NoRepositoryBean
public interface TrampolineRepository<T extends TrampolineEntity> extends JpaRepository<T, UUID> {
}
