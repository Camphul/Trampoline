package com.lucadev.trampoline.autoconfigure;

import com.lucadev.trampoline.converter.UuidConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

/**
 * Autoconfiguration to provide a {@link UuidConverter} bean.
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@ConditionalOnClass(Converter.class)
public class UuidConverterAutoConfiguration {

    /**
     * Bean definition for our {@link UUID} converter. In this case our {@link UuidConverter}
     * @return a converter for converting a {@link String} into a {@link UUID}
     */
    @Bean
    @ConditionalOnMissingBean
    public Converter<String, UUID> uuidConverter() {
        return new UuidConverter();
    }

}
