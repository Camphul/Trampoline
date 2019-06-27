package com.lucadev.trampoline.security.authentication;

/**
 * Which identification field is used to authenticate and authorize users.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 12-5-18
 */
public enum IdentificationField {

	/**
	 * Identify/authorize using user username.
	 */
	USERNAME,

	/**
	 * Identify/authorize using username or email.
	 */
	USERNAME_OR_EMAIL

}
