package com.lucadev.trampoline.apibrick.web.controller;

import com.lucadev.trampoline.apibrick.web.handler.CrudControllerHandler;
import com.lucadev.trampoline.data.entity.TrampolineEntity;
import com.lucadev.trampoline.data.service.CrudService;
import com.lucadev.trampoline.web.model.SuccessResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

/**
 * Abstract crud controller.
 *
 * @param <T> the entity.
 * @param <C> the crud service.
 * @param <H> the controller handler.
 * @param <D> the full dto/model.
 * @param <S> the summary dto/model.
 * @param <P> the post request body.
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/19/20
 */
public abstract class AbstractCrudController<T extends TrampolineEntity, C extends CrudService<T>, H extends CrudControllerHandler<T, C, D, S, P>, D, S, P>
		implements CrudController<T, D, S, P> {

	private final CrudControllerHandler<T, C, D, S, P> handler;

	/**
	 * Construct controller.
	 *
	 * @param handler controller handler.
	 */
	public AbstractCrudController(CrudControllerHandler<T, C, D, S, P> handler) {
		if (handler == null) {
			throw new NullPointerException("Handler cannot be null.");
		}
		this.handler = handler;
	}

	/**
	 * Get crud controller handler.
	 *
	 * @return the controller handler.
	 */
	public CrudControllerHandler<T, C, D, S, P> getHandler() {
		return this.handler;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping
	public ResponseEntity<Page<S>> findAll(Pageable pageable) {
		return getHandler().findAll(pageable);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping("/{id}")
	public ResponseEntity<D> findById(@PathVariable("id") UUID id) {
		return getHandler().findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping
	public ResponseEntity<D> create(@RequestBody P model) {
		return getHandler().create(model);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<SuccessResponse> delete(@PathVariable("id") UUID id) {
		return getHandler().delete(id);
	}

}
