package com.lucadev.trampoline.autoconfigure;

import com.lucadev.trampoline.converter.UUIDConverter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class UUIDConverterAutoConfigurationTest {

    private static final String STATIC_UUID_STRING = "439948e5-192f-4980-8d70-71787265e1a5";
    private AnnotationConfigApplicationContext context;

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext();
    }

    @After
    public void tearDown() throws Exception {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void registersUuidConverterAutomatically() {
        this.context.register(UuidConverterAutoConfiguration.class);
        this.context.refresh();

        Converter<String, UUID> uuidConverter = this.context.getBean("uuidConverter", Converter.class);
        assertTrue(uuidConverter instanceof UUIDConverter);
    }

    @Test
    public void customUuidConverterBean() {
        this.context.register(CustomUuidConfiguration.class, UuidConverterAutoConfiguration.class);
        this.context.refresh();

        Converter<String, UUID> uuidConverter = this.context.getBean("uuidConverter", Converter.class);
        assertEquals(STATIC_UUID_STRING, uuidConverter.convert("test").toString());
        assertEquals(STATIC_UUID_STRING, uuidConverter.convert("test2").toString());
    }

    @Configuration
    protected static class CustomUuidConfiguration {
        @Bean
        public Converter<String, UUID> uuidConverter() {
            return s -> UUID.fromString(STATIC_UUID_STRING);
        }
    }

}