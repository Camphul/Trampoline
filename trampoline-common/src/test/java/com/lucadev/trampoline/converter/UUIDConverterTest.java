package com.lucadev.trampoline.converter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class UUIDConverterTest {

    private UUIDConverter uuidConverter;

    @Before
    public void before() {
        this.uuidConverter = new UUIDConverter();
    }

    @After
    public void tearDown() {
        this.uuidConverter = null;
    }

    @Test
    public void shouldSucceedConvert() {
        String expectedUuid = "197ac149-798d-4e79-b3d6-d427bc39664f";
        UUID result = uuidConverter.convert(expectedUuid);
        assertEquals(expectedUuid, result.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailConvert() {
        String errorString = "this is not an uuid";
        UUID result = uuidConverter.convert(errorString);
    }

}