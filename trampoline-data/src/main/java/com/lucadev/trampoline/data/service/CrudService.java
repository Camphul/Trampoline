package com.lucadev.trampoline.data.service;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface defining methods to execute crud operations as a service.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/19/20
 */
public interface CrudService<T extends TrampolineEntity> {

	/**
	 * Persist entity.
	 *
	 * @param entity entity to persist.
	 * @return persisted entity.
	 */
	Optional<T> save(T entity);

	/**
	 * Update entity.
	 *
	 * @param entity entity to persist.
	 * @return persisted entity.
	 */
	T update(T entity);

	/**
	 * Find all entities.
	 *
	 * @return all found entities.
	 */
	List<T> findAll();

	/**
	 * Find all entities using Spring data pagination.
	 *
	 * @param pageable page info.
	 * @return paginated entities.
	 */
	Page<T> findAll(Pageable pageable);

	/**
	 * Find entity by id.
	 *
	 * @param id entity id.
	 * @return entity.
	 */
	Optional<T> findById(UUID id);

	/**
	 * Delete entity by id.
	 *
	 * @param id delete entity.
	 */
	void deleteById(UUID id);

	/**
	 * Delete entity.
	 *
	 * @param entity entity to delete.
	 */
	default void delete(T entity) {
		if (entity == null) {
			throw new NullPointerException("Cannot delete null entity.");
		}
		deleteById(entity.getId());
	}

}
