package com.lucadev.trampoline.converter;

import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

/**
 * A Spring {@link Converter} to converter a {@link String} into a {@link UUID}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class UUIDConverter implements Converter<String, UUID> {

    /**
     * Convert a String to a {@link UUID}
     * @param s the source String
     * @return null if s is null or empty. Else the UUID from s
     */
    @Override
    public UUID convert(String s) {
        if (s == null || s.isEmpty())
            return null;

        return UUID.fromString(s);
    }
}