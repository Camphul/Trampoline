package com.lucadev.trampoline.security.jwt.support;

import com.lucadev.trampoline.security.web.annotation.EnableIgnoreSecurity;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enables this module.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/3/19
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableIgnoreSecurity
@Import(JwtSecuritySupportConfiguration.class)
public @interface EnableJwtSecuritySupport {

}
