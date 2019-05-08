package com.lucadev.trampoline.security.logging;

import com.lucadev.trampoline.security.logging.aop.UserActivityLoggingAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Annotation which enables the aspect to handle user activity logging.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(UserActivityLoggingAspect.class)
public @interface EnableUserActivityLogging {

}
