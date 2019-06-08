package com.lucadev.trampoline.notify.autoconfigure;

import com.lucadev.trampoline.notify.email.EmailService;
import com.lucadev.trampoline.notify.email.EmailTemplateParser;
import com.lucadev.trampoline.notify.email.TrampolineEmailService;
import com.lucadev.trampoline.notify.email.configuration.EmailConfiguration;
import com.lucadev.trampoline.notify.email.configuration.EmailConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Autoconfigure email service.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@AutoConfigureAfter(NopEmailServiceAutoConfiguration.class)
@ConditionalOnBean(JavaMailSender.class)
@ConditionalOnClass({ EmailService.class, EmailConfiguration.class })
public class EmailServiceAutoConfiguration {

	private final EmailConfigurationProperties emailConfigurationProperties;

	private final EmailTemplateParser emailTemplateParser;

	private final JavaMailSender mailSender;

	@Bean
	@ConditionalOnMissingBean(EmailService.class)
	@ConditionalOnProperty(prefix = "trampoline.notify.email", name = "enabled", havingValue = "true")
	public EmailService emailService() {
		return new TrampolineEmailService(emailConfigurationProperties,
				emailTemplateParser, mailSender);
	}
}
