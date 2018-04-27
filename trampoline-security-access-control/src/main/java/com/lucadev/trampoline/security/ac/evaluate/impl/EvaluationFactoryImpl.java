package com.lucadev.trampoline.security.ac.evaluate.impl;

import com.lucadev.trampoline.security.ac.evaluate.Evaluation;
import com.lucadev.trampoline.security.ac.evaluate.EvaluationFactory;
import com.lucadev.trampoline.security.ac.evaluate.EvaluationResult;

/**
 * {@link EvaluationFactory} implementation.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
public class EvaluationFactoryImpl implements EvaluationFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Evaluation create(EvaluationResult defaultResult) {
        return new EvaluationImpl(defaultResult);
    }

}
