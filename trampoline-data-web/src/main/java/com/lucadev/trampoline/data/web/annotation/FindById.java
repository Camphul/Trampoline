package com.lucadev.trampoline.data.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Method parameter annotation to be used in controller methods.
 * Maps a path variable to a TrampolineEntity.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 5/7/19
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface FindById {

	/**
	 * Path variable id.
	 *
	 * @return requestmapping path variable.
	 */
	String value() default "";

	/**
	 * If we are allowed to return null.
	 *
	 * @return when true it will throw a resource not found exception if the entity could not be found.
	 */
	boolean required() default true;

}
