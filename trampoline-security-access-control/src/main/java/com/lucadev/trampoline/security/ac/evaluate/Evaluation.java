package com.lucadev.trampoline.security.ac.evaluate;

/**
 * Interface to offer evaluation for access control
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
public interface Evaluation {

    /**
     * Deny access
     *
     * @return this{@code Evaluation} that will deny access
     */
    Evaluation deny();

    /**
     * Ignore access control
     *
     * @return this {@code Evaluation} that will ignore the access check.
     */
    Evaluation ignore();

    /**
     * Grant access
     *
     * @return this {@code Evaluation} that will grant access
     */
    Evaluation grant();

    /**
     * Get the result of the evaluation.
     *
     * @return the chosen {@link EvaluationResult}
     */
    EvaluationResult getEvaluationResult();
}
