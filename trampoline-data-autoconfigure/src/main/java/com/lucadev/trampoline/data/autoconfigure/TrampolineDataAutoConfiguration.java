package com.lucadev.trampoline.data.autoconfigure;

import com.lucadev.trampoline.data.configuration.TrampolinePersistenceConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@ConditionalOnClass(TrampolinePersistenceConfiguration.class)
public class TrampolineDataAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TrampolinePersistenceConfiguration trampolineDataConfiguration() {
        return new TrampolinePersistenceConfiguration();
    }

}
