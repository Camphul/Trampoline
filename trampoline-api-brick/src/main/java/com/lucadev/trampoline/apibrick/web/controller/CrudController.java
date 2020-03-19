package com.lucadev.trampoline.apibrick.web.controller;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import com.lucadev.trampoline.web.model.SuccessResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * Interface defining crud methods.
 *
 * @param <T> the entity.
 * @param <D> the full dto/model.
 * @param <S> the summary dto/model.
 * @param <P> the post request body.
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/19/20
 */
public interface CrudController<T extends TrampolineEntity, D, S, P> {

	/**
	 * Find all entities using pagination.
	 *
	 * @param pageable pagination details.
	 * @return page with entities mapped to summary dto.
	 */
	ResponseEntity<Page<S>> findAll(Pageable pageable);

	/**
	 * Find entity by id.
	 *
	 * @param id entity id.
	 * @return entity dto.
	 */
	ResponseEntity<D> findById(UUID id);

	/**
	 * Create new entity.
	 *
	 * @param model request model/dto.
	 * @return created entity.
	 */
	ResponseEntity<D> create(P model);

	/**
	 * Delete entity by id.
	 *
	 * @param id entity id to delete.
	 * @return success response.
	 */
	ResponseEntity<SuccessResponse> delete(UUID id);

}
