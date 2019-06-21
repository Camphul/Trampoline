package com.lucadev.trampoline.security.abac.acl;

/**
 * Enum for action attribute in ACL rule.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/21/19
 */
public enum AclRuleAction {

	/**
	 * Allow access when condition meets.
	 */
	ALLOW,

	/**
	 * Deny access when condition meets.
	 */
	DENY
}
