package com.lucadev.trampoline.security.abac.policy.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.lucadev.trampoline.security.abac.policy.PolicyDefinition;
import com.lucadev.trampoline.security.abac.policy.PolicyRule;
import com.lucadev.trampoline.security.abac.spel.SpelDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.expression.Expression;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Loads policy rules from a Json file.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 20-5-18
 */
public class JsonFilePolicyDefinition implements PolicyDefinition {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonFilePolicyDefinition.class);

	private final String policyFilePath;
	private List<PolicyRule> rules;

	public JsonFilePolicyDefinition(String jsonFilePath) throws IOException {
		this.policyFilePath = jsonFilePath;
		loadPolicyRules();
	}


	private void loadPolicyRules() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Expression.class, new SpelDeserializer());
		mapper.registerModule(module);
		File file = new ClassPathResource(policyFilePath).getFile();
		if (!file.exists()) {
			throw new FileNotFoundException("Cannot load non existent resource");
		}

		try {
			LOGGER.debug("[loadPolicyRules] Checking policy file at: {}", policyFilePath);
			rules = mapper.readValue(file,
					JsonPolicyFileModel.class).getPolicies();
			LOGGER.info("[loadPolicyRules] Policy loaded successfully.");
		} catch (JsonMappingException e) {
			LOGGER.error("An error occurred while parsing the policy file.", e);
		} catch (IOException e) {
			LOGGER.error("An error occurred while reading the policy file.", e);
		}
	}

	@Override
	public List<PolicyRule> findAllPolicyRules() {
		return rules;
	}

	@Override
	public boolean hasPolicyRule(String name) {
		return rules.stream().anyMatch(p -> p.getName().equals(name));
	}

	/**
	 * Adds an in-memory policy rule.
	 *
	 * @param policyRule the policy rule to add
	 */
	@Override
	public PolicyRule addPolicyRule(PolicyRule policyRule) {
		rules.add(policyRule);
		return policyRule;
	}

	@Override
	public PolicyRule updatePolicyRule(PolicyRule policyRule) {
		for (PolicyRule rule : rules) {
			if (rule.getName().equals(policyRule.getName())) {
				rule.setCondition(policyRule.getCondition());
				rule.setTarget(policyRule.getTarget());
				rule.setDescription(policyRule.getDescription());
				return rule;
			}
		}
		//policy not found
		return null;
	}

	/**
	 * Json model that is used to load the policy file.
	 */
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class JsonPolicyFileModel {
		private List<PolicyRule> policies;
	}
}
