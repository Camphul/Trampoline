package com.lucadev.trampoline.notify.autoconfigure;

import com.lucadev.trampoline.notify.email.EmailTemplateParser;
import com.lucadev.trampoline.notify.email.ThymeleafEmailTemplateParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;

/**
 * Autoconfigure template parser.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass(EmailTemplateParser.class)
public class EmailTemplateParserAutoConfiguration {

	private final TemplateEngine templateEngine;

	/**
	 * Template parser bean definition.
	 * @return thymeleaf if no other bean has been defined yet.
	 */
	@Bean
	@ConditionalOnMissingBean(EmailTemplateParser.class)
	public EmailTemplateParser templateParser() {
		log.debug("Using default (thymeleaf) email template parser.");
		return new ThymeleafEmailTemplateParser(this.templateEngine);
	}

}
