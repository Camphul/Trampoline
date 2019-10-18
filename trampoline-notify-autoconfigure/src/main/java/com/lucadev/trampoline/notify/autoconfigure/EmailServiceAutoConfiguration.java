package com.lucadev.trampoline.notify.autoconfigure;

import com.lucadev.trampoline.notify.email.EmailService;
import com.lucadev.trampoline.notify.email.EmailTemplateParser;
import com.lucadev.trampoline.notify.email.TrampolineEmailService;
import com.lucadev.trampoline.notify.email.configuration.EmailConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Autoconfigure email service.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableAsync
@EnableConfigurationProperties(EmailConfigurationProperties.class)
@ConditionalOnClass({ JavaMailSender.class, EmailService.class })
public class EmailServiceAutoConfiguration {

	private final JavaMailSender javaMailSender;

	private final EmailConfigurationProperties emailConfigurationProperties;

	private final EmailTemplateParser emailTemplateParser;

	@Bean
	@ConditionalOnMissingBean(EmailService.class)
	public EmailService emailService() {
		return new TrampolineEmailService(this.emailConfigurationProperties,
				this.emailTemplateParser, this.javaMailSender);
	}

}
