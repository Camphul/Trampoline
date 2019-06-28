package com.lucadev.trampoline.notify.email;

import com.lucadev.trampoline.notify.email.configuration.EmailConfigurationProperties;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper to builder emails.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Getter
@ToString
public final class EmailBuilder {

	private String from;

	private String to;

	private String subject;

	private String template;

	private Map<String, Object> model;

	/**
	 * Constructs the builder using configured defaults.
	 * @param configuration the email properties.
	 */
	private EmailBuilder(EmailConfigurationProperties configuration) {
		this.model = new HashMap<>();
		this.from = configuration.getDefaults().getFrom();
		this.template = configuration.getDefaults().getTemplate();
		this.subject = configuration.getDefaults().getSubject();
	}

	/**
	 * Create a new builder.
	 * @param configurationProperties configuration for defaults and such.
	 * @return new email builder.
	 */
	public static EmailBuilder create(
			EmailConfigurationProperties configurationProperties) {
		return new EmailBuilder(configurationProperties);
	}

	/**
	 * To who will the message be sent.
	 * @param to the receiving address.
	 * @return the builder.
	 */
	public EmailBuilder to(String to) {
		this.to = to;
		return this;
	}

	/**
	 * From which address to we send the email.
	 * @param from sender address.
	 * @return the builder.
	 */
	public EmailBuilder from(String from) {
		this.from = from;
		return this;
	}

	/**
	 * Specify which template to use.
	 * @param template email template.
	 * @return the builder.
	 */
	public EmailBuilder withTemplate(String template) {
		this.template = template;
		return this;
	}

	/**
	 * Specify email subject.
	 * @param subject email subject.
	 * @return the builder.
	 */
	public EmailBuilder withSubject(String subject) {
		this.subject = subject;
		return this;
	}

	/**
	 * Set the model which is used to render the template.
	 * @param model model data.
	 * @return the builder.
	 */
	public EmailBuilder withModel(Map<String, Object> model) {
		this.model = model;
		return this;
	}

	/**
	 * Puts all attributes in the given attribute into the email model.
	 * @param model the model to get all attributes from.
	 * @return the builder.
	 */
	public EmailBuilder withAllAttributes(Map<String, Object> model) {
		this.model.putAll(model);
		return this;
	}

	/**
	 * Set a model attribute.
	 * @param key the name of the attribute.
	 * @param value value of the attribute.
	 * @return the builder.
	 */
	public EmailBuilder withAttribute(String key, Object value) {
		this.model.put(key, value);
		return this;
	}

}
