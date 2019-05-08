package com.lucadev.trampoline.security.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When a method is annotated with {@link LogUserActivity} you may want to know the object being acted upon.
 * Add this annotation to a parameter to specify the object related to the activity.
 * The object will be set as {@code actedUpon} inside the {@link UserActivity}.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActingUpon {

}
