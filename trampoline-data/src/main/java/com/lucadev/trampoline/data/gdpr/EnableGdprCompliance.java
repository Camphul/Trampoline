package com.lucadev.trampoline.data.gdpr;

import com.lucadev.trampoline.data.gdpr.configuration.CryptoConfigurationProperties;
import com.lucadev.trampoline.data.gdpr.repository.CompliantJpaRepositoryFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enables column encryption feature using {@link PersonalData} annotation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/2/19
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableJpaRepositories(basePackages = "",
		repositoryFactoryBeanClass = CompliantJpaRepositoryFactoryBean.class)
@EnableConfigurationProperties(CryptoConfigurationProperties.class)
@Import(GdprComplianceConfiguration.class)
public @interface EnableGdprCompliance {

}
