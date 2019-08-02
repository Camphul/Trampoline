package com.lucadev.trampoline.data.gdpr.crypto;

import com.lucadev.trampoline.data.gdpr.PersonalData;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;

/**
 * Encryption utilities which work strictly with {@link PersonalData}.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/1/19
 */
public class EncryptionUtils {

	/**
	 * Check if the annotation {@link PersonalData} is present on a given field.
	 * @param field
	 * @return
	 */
	public static boolean isFieldEncrypted(Field field) {
		return AnnotationUtils.findAnnotation(field, PersonalData.class) != null;
	}

	/**
	 * Search for name inside properties and return index.
	 * @param name property to search for.
	 * @param properties array of properties to search in.
	 * @return index of name in properties.
	 * @throws IllegalArgumentException when not found.
	 */
	public static int getPropertyIndex(String name, String[] properties)
			throws IllegalArgumentException {
		for (int i = 0; i < properties.length; i++) {
			if (name.equals(properties[i])) {
				return i;
			}
		}
		throw new IllegalArgumentException("No property was found for name " + name);
	}

}
