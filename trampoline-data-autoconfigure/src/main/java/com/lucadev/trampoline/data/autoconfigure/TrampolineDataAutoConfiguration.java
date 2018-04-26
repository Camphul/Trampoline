package com.lucadev.trampoline.data.autoconfigure;

import com.lucadev.trampoline.data.configuration.TrampolinePersistenceConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration which creates a bean for {@link TrampolinePersistenceConfiguration}
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@ConditionalOnClass(TrampolinePersistenceConfiguration.class)
public class TrampolineDataAutoConfiguration {

    /**
     * Bean definition for persistence configuration.
     * @return default persistence configuration.
     */
    @Bean
    @ConditionalOnMissingBean
    public TrampolinePersistenceConfiguration trampolineDataConfiguration() {
        return new TrampolinePersistenceConfiguration();
    }

}
