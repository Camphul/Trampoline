package com.lucadev.trampoline.autoconfigure;

import com.lucadev.trampoline.service.time.SystemTimeProvider;
import com.lucadev.trampoline.service.time.TimeProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for defining a default {@link TimeProvider} bean.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@ConditionalOnClass(TimeProvider.class)
public class TimeProviderAutoConfiguration {

    /**
     * Auto configured bean definition. In this case the {@link SystemTimeProvider}
     * @return default {@link TimeProvider} bean.
     */
    @Bean
    @ConditionalOnMissingBean
    public TimeProvider timeProvider() {
        return new SystemTimeProvider();
    }

}
