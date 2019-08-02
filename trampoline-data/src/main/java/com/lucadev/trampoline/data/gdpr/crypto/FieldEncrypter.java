package com.lucadev.trampoline.data.gdpr.crypto;

import lombok.RequiredArgsConstructor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Encrypts fields.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/1/19
 */
@RequiredArgsConstructor
public class FieldEncrypter {

	private final StringCrypto stringCrypto;

	/**
	 * Encrypt a field.
	 * @param state entity state.
	 * @param propertyNames entity property names.
	 * @param entity actual entity.
	 */
	public void encrypt(Object[] state, String[] propertyNames, Object entity) {
		ReflectionUtils.doWithFields(entity.getClass(),
				field -> encryptField(field, state, propertyNames),
				EncryptionUtils::isFieldEncrypted);
	}

	private void encryptField(Field field, Object[] state, String[] propertyNames) {
		int propertyIndex = EncryptionUtils.getPropertyIndex(field.getName(),
				propertyNames);
		Object currentValue = state[propertyIndex];
		if (currentValue != null) {
			if (!(currentValue instanceof String)) {
				throw new IllegalStateException(
						"Encrypted annotation was used on a non-String field");
			}
			state[propertyIndex] = stringCrypto.encrypt(currentValue.toString());
		}
	}

}
