package com.lucadev.trampoline.data.gdpr.crypto;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.util.Base64;

/**
 * Provides String encrypt and decrypt methods.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/1/19
 */
@Slf4j
public class StringCrypto {

	private final Cipher encryptCipher;

	private final Cipher decryptCipher;

	/**
	 * Construct by creating necessary ciphers.
	 * @param cipherProvider cipher provider.
	 */
	public StringCrypto(CipherProvider cipherProvider) {
		this.encryptCipher = cipherProvider.createCipher(Cipher.ENCRYPT_MODE);
		this.decryptCipher = cipherProvider.createCipher(Cipher.DECRYPT_MODE);
		log.debug("Created encryption and decryption ciphers.");
	}

	/**
	 * Encrypt a given input.
	 * @param input String input to encrypt.
	 * @return the encrypted result encoded with Base64.
	 */
	public String encrypt(String input) {
		// do some encryption
		try {
			return Base64.getEncoder()
					.encodeToString(this.encryptCipher.doFinal(input.getBytes()));
		}
		catch (Exception e) {
			log.error("Failed to encrypt input.", e);
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
		try {
			return new String(
					this.decryptCipher.doFinal(Base64.getDecoder().decode(input)));
		}
		catch (Exception e) {
			log.error("Failed to decrypt input.", e);
			throw new RuntimeException(e);
		}
	}

}
