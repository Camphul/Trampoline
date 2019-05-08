package com.lucadev.trampoline.security.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation will trigger a {@link com.lucadev.trampoline.security.logging.handler.UserActivityHandler}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogUserActivity {

	/**
	 * Log action identifier
	 *
	 * @return activity identifier.
	 */
	String value();

	/**
	 * Category to assign the action to
	 *
	 * @return action category.
	 */
	String category() default "";

	/**
	 * The layer where the activity was ran.
	 *
	 * @return activity layer.
	 */
	ActivityLayer layer() default ActivityLayer.UNDEFINED;

	/**
	 * Description to get a readable logging activity string.
	 *
	 * @return resolver for the message.
	 */
	String description() default "'No description available'";

	/**
	 * Should we parse the description with SPeL?
	 *
	 * @return use SPeL parser for parsing description.
	 */
	boolean spelDescription() default true;

}
