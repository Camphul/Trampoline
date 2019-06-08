package com.lucadev.trampoline.notify.email;

import com.lucadev.trampoline.notify.email.configuration.EmailConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;
import java.util.Map;
import java.util.function.Function;

/**
 * NOP implementation of {@link EmailService}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Slf4j
@RequiredArgsConstructor
public class NopEmailService implements EmailService {

	private final EmailConfigurationProperties configurationProperties;

	@Override
	public EmailBuilder builder() {
		return EmailBuilder.create(configurationProperties);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void send(Function<EmailBuilder, EmailBuilder> builder)
			throws MessagingException {
		send(builder.apply(builder()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void send(EmailBuilder emailBuilder) throws MessagingException {
		send(emailBuilder.getFrom(), emailBuilder.getTo(), emailBuilder.getSubject(),
				emailBuilder.getTemplate(), emailBuilder.getModel());
	}

	/**
	 * Send an email using Spring Email.
	 * @param from coming from this address.
	 * @param to destined for this address.
	 * @param subject with subject.
	 * @param template using email template.
	 * @param model and getting data from this model.
	 * @throws MessagingException when we somehow could not send the email.
	 */
	@Override
	public void send(String from, String to, String subject, String template,
					 Map<String, Object> model) throws MessagingException {
		log.debug("NOP EmailService sending email from {} to {} with subject {} using template {}", from,
				to, subject, template);
	}

	/**
	 * Async version of {@link TrampolineEmailService#send(Function)}.
	 * @param builder function which expects a builder to be returned.
	 * @throws MessagingException
	 */
	@Override
	public void sendAsync(Function<EmailBuilder, EmailBuilder> builder)
			throws MessagingException {
		send(builder);
	}

	/**
	 * Async version of {@link TrampolineEmailService#send(EmailBuilder)}.
	 * @param emailBuilder the email builder.
	 * @throws MessagingException
	 */
	@Override
	public void sendAsync(EmailBuilder emailBuilder) throws MessagingException {
		send(emailBuilder);
	}

	/**
	 * Async version of {@link TrampolineEmailService#send(String, String, String, String, Map)}.
	 * @param from coming from this address.
	 * @param to destined for this address.
	 * @param subject with subject.
	 * @param template using email template.
	 * @param model and getting data from this model.
	 * @throws MessagingException
	 */
	@Override
	public void sendAsync(String from, String to, String subject, String template,
						  Map<String, Object> model) throws MessagingException {
		send(from, to, subject, template, model);
	}

}
