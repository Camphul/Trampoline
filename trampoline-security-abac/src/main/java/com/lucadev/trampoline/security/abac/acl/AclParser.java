package com.lucadev.trampoline.security.abac.acl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Parses ACL file.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/21/19
 */
@Slf4j
@RequiredArgsConstructor
public class AclParser {

	private final InputStream aclSource;

	/**
	 * Parse ACL config.
	 * @return acl rules.
	 * @throws IOException when we failed to parse the config.
	 */
	public List<AclRule> parse() throws IOException {
		log.debug("Parsing ACL file");
		String aclConfig = readInput(this.aclSource, Charset.defaultCharset());
		log.debug("Config:");
		log.debug(aclConfig);
		List<AclRule> rules = new ArrayList<>();
		Pattern rulePattern = Pattern.compile("rule\\s\\w{0,32}?\\s\\{((\\s*?.*?)*?)\\}");
		Pattern ruleNamePattern = Pattern.compile("rule\\s\\w{0,32}?\\s");
		Matcher ruleMatcher = rulePattern.matcher(aclConfig);
		while(ruleMatcher.find()) {
			String rawRule = ruleMatcher.group();
			String ruleConfig = ruleMatcher.group(1);

			String ruleName = null;
			Matcher nameMatcher = ruleNamePattern.matcher(rawRule);
			if(nameMatcher.find()) {
				ruleName = nameMatcher.group().substring(5);
			}

			if(ruleName == null || ruleName.isEmpty()) {
				log.debug("Skipping empty rule.");
				continue;
			}

			log.info("Rule name: \"{}\"", ruleName);
			log.info("Rule config: {}", ruleConfig);
			AclRule rule = parseRule(ruleName, ruleConfig);
			rules.add(rule);
		}
		return rules;
	}

	/**
	 * Parse single rule from acl file.
	 *
	 * @param ruleName
	 * @param textRule text representation of the rule.
	 * @return POJO acl rule.
	 */
	public AclRule parseRule(String ruleName, String textRule) {
		log.info("Parsing rule:");
		log.info(textRule);
		return new AclRule();
	}

	private String readInput(InputStream inputStream, Charset charset) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset))) {
			return br.lines().collect(Collectors.joining(System.lineSeparator()));
		}
	}
}
