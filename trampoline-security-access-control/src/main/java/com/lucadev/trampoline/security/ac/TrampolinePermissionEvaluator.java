package com.lucadev.trampoline.security.ac;

import com.lucadev.trampoline.security.ac.decision.StringConfigAttribute;
import com.lucadev.trampoline.security.authentication.SystemAuthentication;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * {@link PermissionEvaluator} which handles all access-control based aspects.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@AllArgsConstructor
public class TrampolinePermissionEvaluator implements PermissionEvaluator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrampolinePermissionEvaluator.class);
    private final AccessDecisionManager decisionManager;

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
        return hasPrivilege(auth, null, targetType, ((String) permission).toUpperCase());
    }

    private boolean hasPrivilege(Authentication auth, Object domainObject, String targetType, String permission) {
        if (auth instanceof SystemAuthentication) {
            return true;
        }
        LOGGER.debug("hasPrivilege(auth={}, domainObject={}, targetType={}, permission={})",
                auth, domainObject, targetType, permission);
        List<ConfigAttribute> attributes = new ArrayList<>();
        attributes.add(new StringConfigAttribute(permission));

        if (domainObject instanceof Collection) {
            Collection col = (Collection) domainObject;
            for (Object domainElement : col) {
                decisionManager.decide(auth, domainElement, attributes);
            }
        } else {
            decisionManager.decide(auth, domainObject, attributes);
        }
        //Decisionmanager throws an exception when it should deny access
        return true;
    }
}
