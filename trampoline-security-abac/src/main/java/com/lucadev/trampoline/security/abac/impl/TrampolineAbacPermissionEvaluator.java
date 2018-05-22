package com.lucadev.trampoline.security.abac.impl;

import com.lucadev.trampoline.security.abac.AbstractAbacPermissionEvaluator;
import com.lucadev.trampoline.security.abac.policy.PolicyEnforcement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 20-5-18
 */
public class TrampolineAbacPermissionEvaluator extends AbstractAbacPermissionEvaluator {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrampolineAbacPermissionEvaluator.class);

    public TrampolineAbacPermissionEvaluator(PolicyEnforcement policyEnforcement) {
        super(policyEnforcement);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null) {
            throw new AccessDeniedException("Not authenticated");
        }
        Object user = authentication.getPrincipal();
        Map<String, Object> environment = new HashMap<>();

		/*
        Object authDetails = authentication.getDetails();
		if(authDetails != null) {
			if(authDetails instanceof WebAuthenticationDetails) {
				environment.put("remoteAddress", ((WebAuthenticationDetails) authDetails).getRemoteAddress());
			}
		}
		*/
        environment.put("time", new Date());

        LOGGER.debug("hasPermission({}, {}, {})", user, targetDomainObject, permission);
        return policyEnforcement.check(user, targetDomainObject, permission, environment);
    }
}