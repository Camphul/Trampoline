package com.lucadev.trampoline.notify.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

/**
 * Template parser using thymeleaf.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Slf4j
@RequiredArgsConstructor
public class ThymeleafEmailTemplateParser implements EmailTemplateParser {

	private final TemplateEngine templateEngine;

	/**
	 * Parse thymeleaf template using the given model.
	 * @param template template name.
	 * @param model model containing our data.
	 * @return the templated HTML.
	 */
	@Override
	public String process(String template, Map<String, Object> model) {
		log.debug("Parsing thymeleaf template {}", template);
		Context context = new Context();
		context.setVariables(model);
		return templateEngine.process(template, context);
	}
}
