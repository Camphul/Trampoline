package com.lucadev.trampoline.data.gdpr;

import com.lucadev.trampoline.data.gdpr.configuration.CryptoConfigurationProperties;
import com.lucadev.trampoline.data.gdpr.crypto.FieldDecrypter;
import com.lucadev.trampoline.data.gdpr.crypto.FieldEncrypter;
import com.lucadev.trampoline.data.gdpr.crypto.StringCrypto;
import com.lucadev.trampoline.data.gdpr.repository.CompliantRepositoryMethodInterceptor;
import com.lucadev.trampoline.data.gdpr.repository.CompliantRepositoryProxyPostProcessor;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;

/**
 * Configuration when {@link EnableGdprCompliance} is used.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/2/19
 */
class GdprComplianceConfiguration {

	@Bean
	public StringCrypto dataCrypto(
			CryptoConfigurationProperties cryptoConfigurationProperties) {
		return new StringCrypto(cryptoConfigurationProperties);
	}

	@Bean
	public FieldDecrypter fieldDecrypter(
			CryptoConfigurationProperties cryptoConfigurationProperties) {
		return new FieldDecrypter(dataCrypto(cryptoConfigurationProperties));
	}

	@Bean
	public FieldEncrypter fieldEncrypter(
			CryptoConfigurationProperties cryptoConfigurationProperties) {
		return new FieldEncrypter(dataCrypto(cryptoConfigurationProperties));
	}

	@Bean
	public PersonalDataEntityListener personalDataEntityListener(
			EntityManagerFactory entityManagerFactory,
			CryptoConfigurationProperties cryptoConfigurationProperties) {
		return new PersonalDataEntityListener(entityManagerFactory,
				fieldDecrypter(cryptoConfigurationProperties),
				fieldEncrypter(cryptoConfigurationProperties));
	}

	@Bean
	public CompliantRepositoryMethodInterceptor compliantRepositoryMethodInterceptor(
			CryptoConfigurationProperties cryptoConfigurationProperties) {
		return new CompliantRepositoryMethodInterceptor(
				dataCrypto(cryptoConfigurationProperties));
	}

	@Bean
	public CompliantRepositoryProxyPostProcessor compliantRepositoryProxyPostProcessor(
			CryptoConfigurationProperties cryptoConfigurationProperties) {
		return new CompliantRepositoryProxyPostProcessor(
				compliantRepositoryMethodInterceptor(cryptoConfigurationProperties));
	}

}
