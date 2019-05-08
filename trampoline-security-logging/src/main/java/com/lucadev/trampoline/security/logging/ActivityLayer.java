package com.lucadev.trampoline.security.logging;

/**
 * Define where the activity took place.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/9/19
 */
public enum ActivityLayer {
	/**
	 * Controller layer
	 */
	CONTROLLER,

	/**
	 * Service layer
	 */
	SERVICE,

	/**
	 * Other, maybe security, etc...
	 */
	OTHER
}
