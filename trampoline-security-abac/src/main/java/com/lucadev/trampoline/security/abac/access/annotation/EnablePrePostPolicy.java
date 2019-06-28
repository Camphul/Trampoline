package com.lucadev.trampoline.security.abac.access.annotation;

import com.lucadev.trampoline.security.abac.access.PolicyMethodSecurityAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enables {@link PrePolicy} and {@link PostPolicy} annotation functionality.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/28/19
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(PolicyMethodSecurityAspect.class)
public @interface EnablePrePostPolicy {

}
