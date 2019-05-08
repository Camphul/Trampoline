package com.lucadev.trampoline.security.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation will log activity to a handler,
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogUserActivity {

	/**
	 * Activity description.
	 *
	 * @return string representation of the activity that has executed.
	 */
	String value();

	/**
	 * The {@link #value()} is a SPeL expression which needs to be evaluated.
	 * @return false by default. To use spel expressions set this to true.
	 */
	boolean spel() default false;

	/**
	 * Should we still consider logging an activity when the method we log throws a throwable(exception?)
	 * @return true by default. To ignore methods that threw an exception set this to false.
	 */
	boolean logThrowables() default true;


}
