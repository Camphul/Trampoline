package com.lucadev.trampoline.converter;

import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class UuidConverter implements Converter<String, UUID> {

    @Override
    public UUID convert(String s) {
        if (s == null || s.isEmpty())
            return null;

        return UUID.fromString(s);
    }
}