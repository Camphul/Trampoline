package com.lucadev.trampoline.security.jwt.adapter;

import com.lucadev.trampoline.security.jwt.adapter.web.configuration.JwtWebConfigurationProperties;
import com.lucadev.trampoline.security.web.annotation.EnableIgnoreSecurity;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(JwtWebConfigurationProperties.class)
@Import(JwtSecurityAdapterConfiguration.class)
public @interface EnableJwtSecurityAdapter {

}
