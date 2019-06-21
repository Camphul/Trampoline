package com.lucadev.trampoline.security.abac.impl;

import com.lucadev.trampoline.security.abac.PolicyContainer;
import com.lucadev.trampoline.security.abac.acl.AclParser;
import com.lucadev.trampoline.security.abac.acl.AclRule;
import com.lucadev.trampoline.security.abac.configuration.AbacSecurityConfigurationProperties;
import com.lucadev.trampoline.security.abac.persistence.entity.PolicyRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

/**
 * Acl implementation for abac.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/21/19
 */
@Slf4j
public class AclPolicyContainer implements PolicyContainer {

	private final AbacSecurityConfigurationProperties properties;

	public AclPolicyContainer(AbacSecurityConfigurationProperties properties) throws IOException {
		this.properties = properties;
		loadAclRules();
	}

	private void loadAclRules() throws IOException {
		log.debug("Loading ACL rules.");
		ClassPathResource pathResource = new ClassPathResource(properties.getContainer().getAcl().getFilePath());
		AclParser parser = new AclParser(pathResource.getInputStream());
		List<AclRule> rules = parser.parse();
		log.info("Loaded ACL rules.");

	}

	@Override
	public List<PolicyRule> findAllPolicyRules() {
		return null;
	}

	@Override
	public boolean hasPolicyRule(String name) {
		return false;
	}

	@Override
	public PolicyRule addPolicyRule(PolicyRule policyRule) {
		return null;
	}

	@Override
	public PolicyRule updatePolicyRule(PolicyRule policyRule) {
		return null;
	}
}
