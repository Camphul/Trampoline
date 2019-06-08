package com.lucadev.trampoline.notify.email;

import java.util.Map;

/**
 * Interface used to process templates for email.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
public interface EmailTemplateParser {

	/**
	 * Parse template and fill in data.
	 * @param template template name.
	 * @param model model containing our data.
	 * @return parsed html template.
	 */
	String process(String template, Map<String, Object> model);

}
