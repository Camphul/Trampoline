package com.lucadev.trampoline.security.logging;

/**
 * Define where the activity took place.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
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
	 * Filter layer
	 */
	FILTER,

	/**
	 * Other, maybe security, etc...
	 */
	OTHER,

	/**
	 * Not configured.
	 */
	UNDEFINED
}
