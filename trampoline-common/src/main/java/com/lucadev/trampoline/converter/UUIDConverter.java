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
     * Conversion logic
     * @see Converter#convert(Object)
     */
    @Override
    public UUID convert(String s) {
        if (s == null || s.isEmpty())
            return null;

        return UUID.fromString(s);
    }
}