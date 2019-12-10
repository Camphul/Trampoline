package com.lucadev.trampoline.service.time;

import java.time.Instant;

/**
 * A {@link TimeProvider} implementation which uses the default {@link System} class to
 * obtain time.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class SystemTimeProvider implements TimeProvider {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Instant now() {
		return Instant.now();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long unix() {
		return System.currentTimeMillis();
	}

}
