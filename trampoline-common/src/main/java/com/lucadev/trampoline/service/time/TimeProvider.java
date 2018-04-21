package com.lucadev.trampoline.service.time;

import java.util.Date;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public interface TimeProvider {

    /**
     * @return current datetime
     */
    Date now();

    /**
     * @return Unix timestamp
     */
    long unix();

}
