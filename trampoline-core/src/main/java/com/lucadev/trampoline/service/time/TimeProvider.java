package com.lucadev.trampoline.service.time;

import java.util.Date;

/**
 * Interface that defines methods that provide the current time.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public interface TimeProvider {

    /**
     * Returns {@link #unix()} in a {@link Date} object.
     *
     * @return current time inside a {@link Date} object.
     */
    Date now();

    /**
     * Returns the current unix timestamp.
     *
     * @return unix timestamp
     */
    long unix();

}
