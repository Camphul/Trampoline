package com.lucadev.trampoline.data.gdpr.crypto;

import com.lucadev.trampoline.data.gdpr.configuration.CryptoConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Provides {@link Cipher} to components requiring it.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/2/19
 */
@Slf4j
@RequiredArgsConstructor
public class CipherProvider {

	private final CryptoConfigurationProperties cryptoConfigurationProperties;

	/**
	 * Creates a new Cipher.
	 * @param mode mode to either encrypt or decrypt
	 * @return Cipher based on the crypto configuration properties.
	 * @see Cipher
	 */
	public Cipher createCipher(int mode) {
		// do some encryption
		Key key = new SecretKeySpec(
				this.cryptoConfigurationProperties.getKey().getBytes(),
				cryptoConfigurationProperties.getAlgorithm());
		try {
			Cipher c = Cipher.getInstance(this.cryptoConfigurationProperties.getCipher());
			c.init(mode, key);
			return c;
		}
		catch (Exception e) {
			log.error("Failed to create cipher.", e);
			throw new RuntimeException(e);
		}
	}

}
