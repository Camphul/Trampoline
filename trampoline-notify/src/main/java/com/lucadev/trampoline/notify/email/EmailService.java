package com.lucadev.trampoline.notify.email;

import javax.mail.MessagingException;
import java.util.Map;
import java.util.function.Function;

/**
 * Interface which defines basic functionality for handling rich html email.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
public interface EmailService {

	/**
	 * Create a new email.
	 * @return email builder.
	 */
	EmailBuilder builder();

	/**
	 * Send a message built using a {@link EmailBuilder}.
	 * @param builder function which expects a builder to be returned.
	 * @throws MessagingException when for some reason we could not send the email.
	 */
	void send(Function<EmailBuilder, EmailBuilder> builder) throws MessagingException;

	/**
	 * Send email made using the builder.
	 * @param emailBuilder the email builder.
	 * @throws MessagingException when for some reason we could not send the email.
	 */
	void send(EmailBuilder emailBuilder) throws MessagingException;

	/**
	 * Send an email asynchronously.
	 * @param from coming from this address.
	 * @param to destined for this address.
	 * @param subject with subject.
	 * @param template using email template.
	 * @param model and getting data from this model.
	 * @throws MessagingException when we could not send the email.
	 */
	void send(String from, String to, String subject, String template,
			Map<String, Object> model) throws MessagingException;

	/**
	 * Send a message built using a {@link EmailBuilder} asynchronously.
	 * @param builder function which expects a builder to be returned.
	 * @throws MessagingException when for some reason we could not send the email.
	 */
	void sendAsync(Function<EmailBuilder, EmailBuilder> builder)
			throws MessagingException;

	/**
	 * Send email made using the builder asynchronously.
	 * @param emailBuilder the email builder.
	 * @throws MessagingException when for some reason we could not send the email.
	 */
	void sendAsync(EmailBuilder emailBuilder) throws MessagingException;

	/**
	 * Send an email asynchronously.
	 * @param from coming from this address.
	 * @param to destined for this address.
	 * @param subject with subject.
	 * @param template using email template.
	 * @param model and getting data from this model.
	 * @throws MessagingException when we could not send the email.
	 */
	void sendAsync(String from, String to, String subject, String template,
			Map<String, Object> model) throws MessagingException;

}
