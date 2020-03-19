package com.lucadev.trampoline.apibrick.web.handler;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import com.lucadev.trampoline.data.service.CrudService;
import com.lucadev.trampoline.data.service.GenericMapper;
import com.lucadev.trampoline.web.model.SuccessResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * Abstract handler for crud controllers.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/19/20
 */
public abstract class AbstractCrudControllerHandler<T extends TrampolineEntity, C extends CrudService<T>, D, S, P>
		implements CrudControllerHandler<T, C, D, S, P> {

	private final C service;

	private final GenericMapper<T, D, S> mapper;

	/**
	 * Construct handler.
	 *
	 * @param service       crud service.
	 * @param genericMapper mapper.
	 */
	protected AbstractCrudControllerHandler(C service,
											GenericMapper<T, D, S> genericMapper) {
		if (service == null) {
			throw new NullPointerException(
					"Service cannot be null in controller handler.");
		}
		if (genericMapper == null) {
			throw new NullPointerException("Mapper cannot be null.");
		}
		this.service = service;
		this.mapper = genericMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public C getService() {
		return this.service;
	}

	/**
	 * Get mapper.
	 *
	 * @return dto mapper.
	 */
	public GenericMapper<T, D, S> getMapper() {
		return this.mapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Page<S>> findAll(Pageable pageable) {
		Page<T> page = getService().findAll(pageable);
		Page<S> mapped = page.map(entity -> getMapper().toSummary(entity));
		return ResponseEntity.ok(mapped);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<D> findById(UUID id) {
		Optional<T> entityOptional = getService().findById(id);
		if (!entityOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(getMapper().toDto(entityOptional.get()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<SuccessResponse> delete(UUID id) {
		getService().deleteById(id);
		return ResponseEntity.ok(new SuccessResponse());
	}

}
