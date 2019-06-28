package com.lucadev.trampoline.data.web;

/**
 * Interface defining a method to convert a String to a primary key.
 *
 * @param <T> the type of the primary key.
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/28/19
 */
public interface PrimaryKeyMapper<T> {

	/**
	 * Get the class of the primary key type.
	 * @return class of the primary key type.
	 */
	Class<T> getPrimaryKeyType();

	/**
	 * Converts a string to the primary key type.
	 * @param primaryKey the primary key as string.
	 * @return the mapped primary key.
	 */
	T getPrimaryKey(String primaryKey);

}
