package com.lucadev.trampoline.security.abac.access.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Run defined policy after method invocation.
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/10/19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PostPolicy {

	/**
	 * The policy identifier which is used to ask permission.
	 * @return the policy rule action
	 */
	String value();

}
