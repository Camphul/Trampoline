package com.lucadev.trampoline.data.gdpr;

import com.lucadev.trampoline.data.gdpr.configuration.CryptoConfigurationProperties;
import com.lucadev.trampoline.data.gdpr.crypto.CipherProvider;
import com.lucadev.trampoline.data.gdpr.crypto.FieldDecrypter;
import com.lucadev.trampoline.data.gdpr.crypto.FieldEncrypter;
import com.lucadev.trampoline.data.gdpr.crypto.StringCrypto;
import com.lucadev.trampoline.data.gdpr.repository.CompliantRepositoryMethodInterceptor;
import com.lucadev.trampoline.data.gdpr.repository.CompliantRepositoryProxyPostProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;

/**
 * Configuration when {@link EnableGdprCompliance} is used.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/2/19
 */
@RequiredArgsConstructor
class GdprComplianceConfiguration {

	private final EntityManagerFactory entityManagerFactory;

	private final CryptoConfigurationProperties cryptoConfigurationProperties;

	@Bean
	public CipherProvider cipherProvider() {
		return new CipherProvider(this.cryptoConfigurationProperties);
	}

	@Bean
	public StringCrypto stringCrypto() {
		return new StringCrypto(cipherProvider());
	}

	@Bean
	public FieldDecrypter fieldDecrypter() {
		return new FieldDecrypter(stringCrypto());
	}

	@Bean
	public FieldEncrypter fieldEncrypter() {
		return new FieldEncrypter(stringCrypto());
	}

	@Bean
	public PersonalDataEntityListener personalDataEntityListener() {
		return new PersonalDataEntityListener(this.entityManagerFactory, fieldDecrypter(),
				fieldEncrypter());
	}

	@Bean
	public CompliantRepositoryMethodInterceptor compliantRepositoryMethodInterceptor() {
		return new CompliantRepositoryMethodInterceptor(stringCrypto());
	}

	@Bean
	public CompliantRepositoryProxyPostProcessor compliantRepositoryProxyPostProcessor() {
		return new CompliantRepositoryProxyPostProcessor(
				compliantRepositoryMethodInterceptor());
	}

}
