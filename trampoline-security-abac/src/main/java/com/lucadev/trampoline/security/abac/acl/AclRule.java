package com.lucadev.trampoline.security.abac.acl;

import org.springframework.expression.Expression;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/21/19
 */
public class AclRule {

	private String name;

	private String description;

	private String operation;

	private String resource;

	private Expression condition;

	private AclRuleAction action;
}
