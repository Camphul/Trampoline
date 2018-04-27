package com.lucadev.trampoline.security.ac;

import com.lucadev.trampoline.security.ac.evaluate.Evaluation;
import org.springframework.security.core.Authentication;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
public interface AccessEvaluator {

    /**
     * Check if we have access
     * @param eval the result containing our check evaluation
     * @param auth the current authentication object.
     * @param domainObject the domain object or id.
     * @param targetType the target type(type of the domain object)
     * @param permission the permission to check access on.
     */
    void checkAccess(Evaluation eval, Authentication auth, Object domainObject, String targetType, String permission);
}
