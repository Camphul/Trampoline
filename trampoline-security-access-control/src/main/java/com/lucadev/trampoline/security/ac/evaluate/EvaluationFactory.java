package com.lucadev.trampoline.security.ac.evaluate;

/**
 * Factory for creating evaluation objects.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
public interface EvaluationFactory {

    /**
     * Create a new {@link Evaluation}
     *
     * @param defaultResult the default {@link EvaluationResult}
     * @return the created {@link Evaluation}
     */
    Evaluation create(EvaluationResult defaultResult);

}
