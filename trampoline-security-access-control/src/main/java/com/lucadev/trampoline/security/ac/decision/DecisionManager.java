package com.lucadev.trampoline.security.ac.decision;

import com.lucadev.trampoline.security.ac.AccessEvaluator;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * Interface which defines methods required to decide on access
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
public interface DecisionManager {

    /**
     * Decide on permission
     *
     * @param auth
     * @param domainObject
     * @param targetType
     * @param permission
     * @return
     */
    boolean decide(Authentication auth, Object domainObject, String targetType, String permission);

    /**
     * A {@link List} of {@link AccessEvaluator} beans.
     *
     * @return
     */
    List<AccessEvaluator> getAccessEvaluators();

}
