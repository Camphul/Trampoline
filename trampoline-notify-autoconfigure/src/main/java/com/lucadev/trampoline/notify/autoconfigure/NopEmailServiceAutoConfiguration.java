package com.lucadev.trampoline.notify.autoconfigure;

import com.lucadev.trampoline.notify.email.EmailService;
import com.lucadev.trampoline.notify.email.NopEmailService;
import com.lucadev.trampoline.notify.email.configuration.EmailConfiguration;
import com.lucadev.trampoline.notify.email.configuration.EmailConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfigure NOP(no operation) email service to use when no mail server has been configured..
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass({ EmailService.class, EmailConfiguration.class })
public class NopEmailServiceAutoConfiguration {

	private final EmailConfigurationProperties emailConfigurationProperties;

	@Bean
	@ConditionalOnMissingBean(EmailService.class)
	@ConditionalOnProperty(prefix = "trampoline.notify.email", name = "enabled", havingValue = "false", matchIfMissing = true)
	public EmailService nopEmailService() {
		return new NopEmailService(emailConfigurationProperties);
	}

}
