package com.lucadev.trampoline.data.service;

import java.util.List;

/**
 * Interface defining basic mapping methods.
 *
 * @param <T> type to map.
 * @param <D> dto type.
 * @param <S> summary type.
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/19/20
 */
public interface GenericMapper<T, D, S> {

	/**
	 * Map to dto.
	 *
	 * @param entity entity to map.
	 * @return dto.
	 */
	D toDto(T entity);

	/**
	 * Map list to dto's.
	 *
	 * @param entities entities to map.
	 * @return dto list.
	 */
	List<D> toDtos(List<T> entities);

	/**
	 * Map to summary.
	 *
	 * @param entity entity to map.
	 * @return summary dto.
	 */
	S toSummary(T entity);

	/**
	 * Map to list of summaries.
	 *
	 * @param entities entities to map.
	 * @return summaries.
	 */
	List<S> toSummaries(List<T> entities);

}
