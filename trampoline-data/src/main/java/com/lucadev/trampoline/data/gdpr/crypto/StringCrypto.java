package com.lucadev.trampoline.data.gdpr.crypto;

import com.lucadev.trampoline.data.gdpr.configuration.CryptoConfigurationProperties;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

/**
 * Provides String encrypt and decrypt methods.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/1/19
 */
public class StringCrypto {

	private final String algorithm;

	private final byte[] key;

	/**
	 * Construct String crypto.
	 * @param cryptoConfigurationProperties crypto properties.
	 */
	public StringCrypto(CryptoConfigurationProperties cryptoConfigurationProperties) {
		this.algorithm = cryptoConfigurationProperties.getAlgorithm();
		this.key = cryptoConfigurationProperties.getKey().getBytes();
	}

	/**
	 * Encrypt a given input.
	 * @param input String input to encrypt.
	 * @return the encrypted result encoded with Base64.
	 */
	public String encrypt(String input) {
		// do some encryption
		Key key = new SecretKeySpec(this.key, "AES");
		try {
			Cipher c = Cipher.getInstance(algorithm);
			c.init(Cipher.ENCRYPT_MODE, key);
			return Base64.getEncoder().encodeToString(c.doFinal(input.getBytes()));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Decrypt a String.
	 * @param input the base64 encrypted String.
	 * @return decrypted String.
	 */
	public String decrypt(String input) {
		// do some decryption
		Key key = new SecretKeySpec(this.key, "AES");
		try {
			Cipher c = Cipher.getInstance(algorithm);
			c.init(Cipher.DECRYPT_MODE, key);
			return new String(c.doFinal(Base64.getDecoder().decode(input)));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
