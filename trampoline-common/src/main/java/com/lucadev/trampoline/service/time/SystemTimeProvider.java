package com.lucadev.trampoline.service.time;

import java.util.Date;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class SystemTimeProvider implements TimeProvider {

    @Override
    public Date now() {
        return new Date(unix());
    }

    @Override
    public long unix() {
        return System.currentTimeMillis();
    }
}