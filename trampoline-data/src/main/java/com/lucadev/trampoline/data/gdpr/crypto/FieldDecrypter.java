package com.lucadev.trampoline.data.gdpr.crypto;

import lombok.RequiredArgsConstructor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Decrypt a field.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/1/19
 */
@RequiredArgsConstructor
public class FieldDecrypter {

	private final StringCrypto stringCrypto;

	/**
	 * Decrypt
	 * @param state entity state.
	 * @param propertyNames entity property names.
	 * @param entity actual entity.
	 */
	public void decrypt(Object[] state, String[] propertyNames, Object entity) {
		ReflectionUtils.doWithFields(entity.getClass(),
				field -> decryptField(field, state, propertyNames),
				EncryptionUtils::isFieldEncrypted);
	}

	/**
	 * Decrypts a field.
	 * @param field the field to decrypt.
	 * @param state entity state.
	 * @param propertyNames names of the properties.
	 */
	private void decryptField(Field field, Object[] state, String[] propertyNames) {
		int propertyIndex = EncryptionUtils.getPropertyIndex(field.getName(),
				propertyNames);
		Object currentValue = state[propertyIndex];
		if (currentValue != null) {
			if (!(currentValue instanceof String)) {
				throw new IllegalStateException(
						"Encrypted annotation was used on a non-String field");
			}
			state[propertyIndex] = stringCrypto.decrypt(currentValue.toString());
		}
	}

}
