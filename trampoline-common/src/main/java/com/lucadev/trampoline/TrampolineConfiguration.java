package com.lucadev.trampoline;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration used to load trampoline through the {@link EnableTrampoline} annotation.
 * Package private since we do not want this class to be public.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 26-4-18
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableCaching
class TrampolineConfiguration {
}
