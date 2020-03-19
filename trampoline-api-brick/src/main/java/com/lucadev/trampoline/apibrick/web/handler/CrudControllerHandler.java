package com.lucadev.trampoline.apibrick.web.handler;

import com.lucadev.trampoline.apibrick.web.controller.CrudController;
import com.lucadev.trampoline.data.entity.TrampolineEntity;
import com.lucadev.trampoline.data.service.CrudService;

/**
 * Interface defining methods used to handle controller endpoints from a
 * {@link CrudController}.
 *
 * @param <T> the entity.
 * @param <C> the crud service.
 * @param <D> the full dto/model.
 * @param <S> the summary dto/model.
 * @param <P> the post request body.
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/19/20
 */
public interface CrudControllerHandler<T extends TrampolineEntity, C extends CrudService<T>, D, S, P>
		extends CrudController<T, D, S, P> {

	/**
	 * Get crud service.
	 *
	 * @return crud service.
	 */
	C getService();

}
