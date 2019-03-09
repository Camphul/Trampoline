package com.lucadev.trampoline.security.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogUserActivity {

	/**
	 * The identifier of which log type it is.
	 * @return
	 */
	String value() default "undefined";

	/**
	 * Category the log should be submitted to.
	 * @return
	 */
	String category() default "logs";

	/**
	 * In which layer is this annotation place.
	 * @return
	 */
	ActivityLayer layer() default ActivityLayer.UNDEFINED;

}
