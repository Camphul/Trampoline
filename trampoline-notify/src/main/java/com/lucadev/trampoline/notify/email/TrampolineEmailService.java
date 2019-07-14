package com.lucadev.trampoline.notify.email;

import com.lucadev.trampoline.notify.email.configuration.EmailConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * Default {@link EmailService} implementation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Slf4j
@RequiredArgsConstructor
public class TrampolineEmailService implements EmailService {

	private final EmailConfigurationProperties configurationProperties;

	private final EmailTemplateParser templateParser;

	private final JavaMailSender mailSender;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmailBuilder builder() {
		return EmailBuilder.create(this, this.configurationProperties);
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
	@Async /*
			 * Only this method has to be async annotated since the other methods delegate
			 * to this.
			 */
	@Override
	public void send(String from, String to, String subject, String template,
			Map<String, Object> model) throws MessagingException {
		log.debug("Sending email from {} to {} with subject {} using template {}", from,
				to, subject, template);
		MimeMessage message = this.mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		String content = this.templateParser.process(template, model);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setFrom(from);
		message.setContent(content, "text/html; charset=utf-8");
		// send mesage
		this.mailSender.send(message);
		log.debug("Sent email..");
	}

	/**
	 * Async version of {@link TrampolineEmailService#send(EmailBuilder)}.
	 * @param emailBuilder the email builder.
	 * @throws MessagingException when for some reason we could not send the message.
	 */
	@Async
	@Override
	public void sendAsync(EmailBuilder emailBuilder) throws MessagingException {
		send(emailBuilder);
	}

	/**
	 * Async version of
	 * {@link TrampolineEmailService#send(String, String, String, String, Map)}.
	 * @param from coming from this address.
	 * @param to destined for this address.
	 * @param subject with subject.
	 * @param template using email template.
	 * @param model and getting data from this model.
	 * @throws MessagingException when for some reason we could not send the message.
	 */
	@Async
	@Override
	public void sendAsync(String from, String to, String subject, String template,
			Map<String, Object> model) throws MessagingException {
		send(from, to, subject, template, model);
	}

}
