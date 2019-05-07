package com.lucadev.trampoline;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Spring meta-annotation for loading trampoline services correctly.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 26-4-18
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(TrampolineConfiguration.class)
public @interface EnableTrampoline {
}
