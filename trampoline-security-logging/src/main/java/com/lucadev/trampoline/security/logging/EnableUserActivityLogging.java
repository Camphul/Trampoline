package com.lucadev.trampoline.security.logging;

import com.lucadev.trampoline.security.logging.aop.UserActivityLoggingAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(UserActivityLoggingAspect.class)
public @interface EnableUserActivityLogging {
}
