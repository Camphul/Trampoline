package com.lucadev.trampoline.security.ac.evaluate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum with possible results for a vote.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@AllArgsConstructor
@Getter
public enum EvaluationResult {

    /**
     * Deny access
     */
    DENY(-1),

    /**
     * Don't do anything with this result
     */
    IGNORE(0),

    /**
     * Grant access
     */
    GRANT(1);

    private final int weight;
}
