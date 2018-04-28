package com.lucadev.trampoline.security.ac;

import com.lucadev.trampoline.security.ac.decision.DecisionManager;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * {@link PermissionEvaluator} which handles all access-control based aspects.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@AllArgsConstructor
public class TrampolinePermissionEvaluator implements PermissionEvaluator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrampolinePermissionEvaluator.class);
    private final DecisionManager decisionManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(Authentication auth, Object domainObject, Object permission) {
        if ((auth == null) || (domainObject == null) || !(permission instanceof String)) {
            return false;
        }
        String targetType = null;
        if (domainObject instanceof String) {
            targetType = String.valueOf(domainObject);
        } else {
            targetType = domainObject.getClass().getSimpleName().toUpperCase();
        }
        return hasPrivilege(auth, domainObject, targetType, ((String) permission).toUpperCase());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        if (auth == null || targetType == null || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth, targetId, targetType, ((String) permission).toUpperCase());
    }

    private boolean hasPrivilege(Authentication auth, Object domainObject, String targetType, String permission) {
        LOGGER.info("hasPrivilege(auth={}, domainObject={}, targetType={}, permission={})",
                auth, domainObject, targetType, permission);
        return decisionManager.decide(auth, domainObject, targetType, permission);
    }
}
