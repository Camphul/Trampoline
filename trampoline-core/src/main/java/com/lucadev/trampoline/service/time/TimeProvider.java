package com.lucadev.trampoline.service.time;

import java.time.Instant;

/**
 * Interface that defines methods that provide the current time.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public interface TimeProvider {

	/**
	 * Returns {@link #unix()} in a {@link Instant} object.
	 * @return current time inside a {@link Instant} object.
	 */
	Instant now();

	/**
	 * Returns the current unix timestamp.
	 * @return unix timestamp
	 */
	long unix();

}
