package com.lucadev.trampoline.data.service;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import com.lucadev.trampoline.data.repository.TrampolineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Abstract crud service.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/19/20
 */
@Slf4j
public class AbstractCrudService<E extends TrampolineEntity, R extends TrampolineRepository<E>>
		implements CrudService<E> {

	private final R repository;

	/**
	 * Construct service with repository.
	 *
	 * @param repository data repository.
	 */
	public AbstractCrudService(R repository) {
		if (repository == null) {
			throw new NullPointerException("Repository for entity cannot be null.");
		}
		this.repository = repository;
	}

	/**
	 * Get repository used.
	 *
	 * @return crud repository.
	 */
	public R getRepository() {
		return this.repository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<E> save(E entity) {
		return Optional.ofNullable(this.repository.save(entity));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E update(E entity) {
		return save(entity)
				.orElseThrow(() -> new NullPointerException("Could not save entity."));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<E> findAll() {
		return this.repository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<E> findAll(Pageable pageable) {
		return this.repository.findAll(pageable);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<E> findById(UUID id) {
		return this.repository.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteById(UUID id) {
		this.repository.deleteById(id);
	}

}
