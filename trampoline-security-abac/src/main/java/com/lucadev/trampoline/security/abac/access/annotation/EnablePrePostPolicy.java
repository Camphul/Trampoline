package com.lucadev.trampoline.security.abac.access.annotation;

import com.lucadev.trampoline.security.abac.access.PolicyMethodSecurityAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
