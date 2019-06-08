package com.lucadev.trampoline.notify.autoconfigure;

import com.lucadev.trampoline.notify.email.EmailService;
import com.lucadev.trampoline.notify.email.EmailTemplateParser;
import com.lucadev.trampoline.notify.email.TrampolineEmailService;
import com.lucadev.trampoline.notify.email.configuration.EmailConfiguration;
import com.lucadev.trampoline.notify.email.configuration.EmailConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Autoconfigure email service.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass({ EmailService.class, EmailConfiguration.class })
@ConditionalOnProperty(prefix = "trampoline.notify.email", name = "enabled",
		havingValue = "true")
public class EmailServiceAutoConfiguration {

	private final EmailConfigurationProperties emailConfigurationProperties;

	private final EmailTemplateParser emailTemplateParser;

	private final JavaMailSender mailSender;

	@Bean
	@ConditionalOnMissingBean(EmailService.class)
	public EmailService emailService() {
		return new TrampolineEmailService(emailConfigurationProperties,
				emailTemplateParser, mailSender);
	}

}
