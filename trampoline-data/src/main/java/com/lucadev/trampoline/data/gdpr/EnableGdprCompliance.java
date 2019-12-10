package com.lucadev.trampoline.data.gdpr;

import com.lucadev.trampoline.data.gdpr.configuration.CryptoConfigurationProperties;
import com.lucadev.trampoline.data.gdpr.repository.CompliantJpaRepositoryFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enables column encryption feature through {@link PersonalData} and custom beans.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/2/19
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableJpaRepositories
@EnableConfigurationProperties(CryptoConfigurationProperties.class)
@Import(GdprComplianceConfiguration.class)
public @interface EnableGdprCompliance {

	/**
	 * Base packages to scan for Jpa repositories.
	 * @return array of basepackages to scan for Jpa repositories.
	 * @see EnableJpaRepositories
	 */
	@AliasFor(annotation = EnableJpaRepositories.class, attribute = "basePackages")
	String[] basePackages() default "";

	/**
	 * Exclude filters for scanning for Jpa repositories. Contains a useful default to not
	 * scan common packages(java, javax, org.springframework, org.hibernate). This
	 * increases startup speed.
	 * @return array of exclude filters
	 * @see EnableJpaRepositories
	 */
	@AliasFor(annotation = EnableJpaRepositories.class, attribute = "excludeFilters")
	ComponentScan.Filter[] excludeFilters() default {
			@ComponentScan.Filter(type = FilterType.REGEX, pattern = "java\\..*"),
			@ComponentScan.Filter(type = FilterType.REGEX, pattern = "javax\\..*"),
			@ComponentScan.Filter(type = FilterType.REGEX,
					pattern = "org\\.springframework\\..*"),
			@ComponentScan.Filter(type = FilterType.REGEX,
					pattern = "org\\.hibernate\\..*"),
			@ComponentScan.Filter(type = FilterType.REGEX,
					pattern = "com\\.fasterxml\\..*"),
			@ComponentScan.Filter(type = FilterType.REGEX, pattern = "org\\.apache\\..*"),
			@ComponentScan.Filter(type = FilterType.REGEX,
					pattern = "org\\.aspectj\\..*") };

	/**
	 * Repository factory bean.
	 * @return Jpa repository factory bean class.
	 * @see JpaRepositoryFactoryBean
	 */
	@AliasFor(annotation = EnableJpaRepositories.class,
			attribute = "repositoryFactoryBeanClass")
	Class<? extends JpaRepositoryFactoryBean> repositoryFactoryBeanClass() default CompliantJpaRepositoryFactoryBean.class;

}
