package com.lucadev.trampoline.service.time;

import java.util.Date;

/**
 * A {@link TimeProvider} implementation which uses the default {@link System} class to obtain time.
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class SystemTimeProvider implements TimeProvider {

    /**
     * {@inheritDoc}
     */
    @Override
    public Date now() {
        return new Date(unix());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long unix() {
        return System.currentTimeMillis();
    }
}