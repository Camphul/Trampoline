package com.lucadev.trampoline.security.logging;

import com.lucadev.trampoline.security.logging.activity.resolver.DefaultUserActivityResolver;
import com.lucadev.trampoline.security.logging.activity.UserActivityResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation will trigger a {@link com.lucadev.trampoline.security.logging.activity.handler.UserActivityHandler}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogUserActivity {

	/**
	 * The identifier which discloses which type of activity it is.
	 * @return
	 */
	String value();

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

	/**
	 * Resolver for user activity message.
	 * @return
	 */
	Class<? extends UserActivityResolver> resolver() default DefaultUserActivityResolver.class;

}
