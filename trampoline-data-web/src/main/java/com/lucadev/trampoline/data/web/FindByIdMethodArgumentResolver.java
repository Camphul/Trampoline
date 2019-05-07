package com.lucadev.trampoline.data.web;

import com.lucadev.trampoline.data.ResourceNotFoundException;
import com.lucadev.trampoline.data.entity.TrampolineEntity;
import com.lucadev.trampoline.data.web.annotation.FindById;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.Map;
import java.util.UUID;

/**
 * Resolves entity id to entity inside pathvariable
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 5/7/19
 */
@RequiredArgsConstructor
public class FindByIdMethodArgumentResolver implements HandlerMethodArgumentResolver {

	private final EntityManager entityManager;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		FindById findAnnotation = parameter.getParameterAnnotation(FindById.class);

		//Only work when this annotation is present.
		if(findAnnotation == null) {
			return false;
		}

		Class paramType = parameter.getParameterType();
		//Require @Entity annotation
		if(!paramType.isAnnotationPresent(Entity.class)) {
			return false;
		}

		//Require superclass to be TrampolineEntity due to UUID id.
		if(!paramType.isAssignableFrom(TrampolineEntity.class)) {
			return false;
		}

		return true;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
								  ModelAndViewContainer modelAndViewContainer,
								  NativeWebRequest request,
								  WebDataBinderFactory webDataBinderFactory) throws Exception {

		FindById findAnnotation = parameter.getParameterAnnotation(FindById.class);
		Class paramType = parameter.getParameterType();
		String name = getPathVariableName(parameter, findAnnotation);
		final Map<String, String> templateVariables
				= (Map<String, String>) request.getAttribute(
				HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
				RequestAttributes.SCOPE_REQUEST);

		String idString = templateVariables.get(name);
		UUID id = UUID.fromString(idString);
		Object entity = entityManager.find(paramType, id);

		if(entity == null && findAnnotation.required()) {
			throw new ResourceNotFoundException("Could not find " + paramType.getSimpleName() + " with id: " + id);
		}
		return entity;
	}

	public String getPathVariableName(MethodParameter parameter, FindById annotation) {
		String name = annotation.value();

		if(name == null || name.isEmpty()) {
			name = parameter.getParameterName();
		}

		return name;
	}
}
