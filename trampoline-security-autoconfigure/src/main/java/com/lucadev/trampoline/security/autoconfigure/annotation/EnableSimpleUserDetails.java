package com.lucadev.trampoline.security.autoconfigure.annotation;

import com.lucadev.trampoline.security.autoconfigure.SimpleUserDetailsWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to use the default {@link org.springframework.security.core.userdetails.UserDetailsService}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/3/19
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableWebSecurity
@ImportAutoConfiguration(SimpleUserDetailsWebSecurityAutoConfiguration.class)
public @interface EnableSimpleUserDetails {
}
