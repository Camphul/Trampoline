package com.lucadev.trampoline.data.autoconfigure;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Autoconfiguration for trampoline-data.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@EnableJpaAuditing
@EnableCaching
@ConditionalOnClass(TrampolineEntity.class)
public class TrampolineDataAutoConfiguration {

}
