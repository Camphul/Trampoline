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
	 * The identifier which discloses which type of activity it is.
	 *
	 * @return activity identifier.
	 */
	String value();

	/**
	 * Category the log should be submitted to.
	 *
	 * @return activity category.
	 */
	String category() default "logs";

	/**
	 * In which layer is this annotation place.
	 *
	 * @return activity layer.
	 */
	ActivityLayer layer() default ActivityLayer.UNDEFINED;

	/**
	 * Resolver for user activity message.
	 *
	 * @return resolver for the message.
	 */
	String description() default "'No description available'";

	/**
	 * Parse description using SPeL
	 * @return use SPeL parser.
	 */
	boolean spelDescription() default true;

}
