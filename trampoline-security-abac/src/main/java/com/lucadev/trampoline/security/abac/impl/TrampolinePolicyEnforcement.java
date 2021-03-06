package com.lucadev.trampoline.security.abac.impl;

import com.lucadev.trampoline.security.abac.PolicyContainer;
import com.lucadev.trampoline.security.abac.PolicyEnforcement;
import com.lucadev.trampoline.security.abac.persistence.entity.PolicyRule;
import com.lucadev.trampoline.security.abac.spel.context.SecurityAccessContext;
import com.lucadev.trampoline.security.abac.spel.context.SecurityAccessContextFactory;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Default Trampoline policy enforcement implementation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 20-5-18
 */
@AllArgsConstructor
public class TrampolinePolicyEnforcement implements PolicyEnforcement {

	private static final Logger logger = LoggerFactory
			.getLogger(TrampolinePolicyEnforcement.class);

	private final SecurityAccessContextFactory securityAccessContextFactory;

	private final PolicyContainer policyContainer;

	/**
	 * Check if we have access to the given resource.
	 * @param subject the identity trying to access the resource.
	 * @param resource the resource which is being accessed
	 * @param action the action that is taken against the resource
	 * @param environment the current context in which the action is taking place
	 * @return the result of the policy check, true means we are allowed to access and
	 * false should deny access.
	 */
	@Override
	public boolean check(Object subject, Object resource, Object action,
			Object environment) {
		// Get all policy rules
		List<PolicyRule> allRules = this.policyContainer.findAllPolicyRules();
		// Wrap the context
		SecurityAccessContext cxt = this.securityAccessContextFactory.create(subject,
				resource, action, environment);
		// Filter the rules according to context.
		List<PolicyRule> matchedRules = filterRules(allRules, cxt);
		// finally, check if any of the rules are satisfied, otherwise return false.
		return checkRules(matchedRules, cxt);
	}

	/**
	 * Check if we have access to the given resource. In this method the Spring
	 * {@link org.springframework.security.core.context.SecurityContext} will be used.
	 * @param resource the resource which is being accessed
	 * @param action the permission required by the action against the resource.
	 * @param environment the current context in which the action is taking place
	 */
	@Override
	public void check(Object resource, Object action, Object environment) {
		Authentication auth = authenticationContext()
				.orElseThrow(() -> new AccessDeniedException("Not authenticated."));

		if (!check(auth.getPrincipal(), resource, action, environment)) {
			throw new AccessDeniedException("Principal has no access to resource.");
		}
	}

	/**
	 * Check if we have access to the given resource. In this method the Spring
	 * {@link org.springframework.security.core.context.SecurityContext} will be used.
	 * @param resource the resource which is being accessed
	 * @param action the permission required by the action against the resource.
	 */
	@Override
	public void check(Object resource, Object action) {
		check(resource, action, environmentContext());
	}

	/**
	 * Filter the list for access.
	 * @param resources the list of resources
	 * @param permission the permission to check
	 * @param <T> the type of resource in the list
	 * @return the filtered list.
	 */
	@Override
	public <T> List<T> filter(List<T> resources, String permission) {
		Map<String, Object> environment = environmentContext();
		Object principal = authenticationContext()
				.orElseThrow(() -> new AccessDeniedException("Not authenticated."))
				.getPrincipal();
		return resources.stream()
				.filter(resource -> check(principal, resource, permission, environment))
				.collect(Collectors.toList());
	}

	/**
	 * Get the environment through the use of Spring context.
	 * @return environment built from Spring context.
	 */
	private Map<String, Object> environmentContext() {
		return new HashMap<>();
	}

	/**
	 * Filters the rules to match the given {@link SecurityAccessContext}.
	 * @param allRules the rules to filter.
	 * @param ctx the {@link SecurityAccessContext} to apply as filter constraints.
	 * @return the filtered {@link List} of {@link PolicyRule} objects.
	 */
	private List<PolicyRule> filterRules(List<PolicyRule> allRules,
			SecurityAccessContext ctx) {
		return allRules.stream().filter(rule -> {
			try {
				return rule.getTarget().getValue(ctx, Boolean.class);
			}
			catch (EvaluationException ex) {
				logger.error("Failed to evaluate PolicyRule target", ex);
				return false;
			}
		}).collect(Collectors.toList());
	}

	/**
	 * Evaluate a List of {@link PolicyRule} objects against the
	 * {@link SecurityAccessContext}.
	 * @param matchedRules the rules to evaluate.
	 * @param ctx the {@link SecurityAccessContext} to evaluate against
	 * @return if the check passed.
	 */
	private boolean checkRules(List<PolicyRule> matchedRules, SecurityAccessContext ctx) {
		StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
		evaluationContext.addPropertyAccessor(new MapAccessor());
		for (PolicyRule rule : matchedRules) {
			try {
				if (rule.getCondition().getValue(evaluationContext, ctx, Boolean.class)) {
					return true;
				}
			}
			catch (EvaluationException ex) {
				logger.info("An error occurred while evaluating PolicyRule.", ex);
			}
		}
		return false;
	}

	/**
	 * Get current authentication.
	 * @return a {@link Authentication} from the security context.
	 */
	private Optional<Authentication> authenticationContext() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return Optional.empty();
		}
		return Optional.of(auth);
	}

}
