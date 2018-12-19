package com.lucadev.trampoline.security.abac.impl;

import com.lucadev.trampoline.security.abac.AbstractAbacPermissionEvaluator;
import com.lucadev.trampoline.security.abac.policy.PolicyEnforcement;
import com.lucadev.trampoline.service.time.TimeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;

/**
 * Default {@link com.lucadev.trampoline.security.abac.AbacPermissionEvaluator} implementation.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 20-5-18
 */
public class TrampolineAbacPermissionEvaluator extends AbstractAbacPermissionEvaluator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrampolineAbacPermissionEvaluator.class);
    private final TimeProvider timeProvider;

    public TrampolineAbacPermissionEvaluator(PolicyEnforcement policyEnforcement, TimeProvider timeProvider) {
        super(policyEnforcement);
        this.timeProvider = timeProvider;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null) {
            throw new AccessDeniedException("Not authenticated");
        }
        Object user = authentication.getPrincipal();
        Map<String, Object> environment = new HashMap<>();

        environment.put("time", timeProvider.now());

        LOGGER.debug("hasPermission({}, {}, {})", user, targetDomainObject, permission);
        return policyEnforcement.check(user, targetDomainObject, permission, environment);
    }
}