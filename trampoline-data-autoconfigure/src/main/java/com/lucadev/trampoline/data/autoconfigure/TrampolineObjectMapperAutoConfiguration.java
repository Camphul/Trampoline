package com.lucadev.trampoline.data.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for a jackson {@link ObjectMapper} bean.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@Configuration
@ConditionalOnMissingBean(ObjectMapper.class)
public class TrampolineObjectMapperAutoConfiguration {

    /**
     * Jackson {@link ObjectMapper} bean definition.
     *
     * @return {@link ObjectMapper} bean
     */
    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


}
