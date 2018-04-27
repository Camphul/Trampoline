package com.lucadev.trampoline.security.ac.evaluate.impl;

import com.lucadev.trampoline.security.ac.evaluate.Evaluation;
import com.lucadev.trampoline.security.ac.evaluate.EvaluationResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * {@link Evaluation} implementation
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@AllArgsConstructor
@ToString
public class EvaluationImpl implements Evaluation {

    private EvaluationResult evaluationResult;

    /**
     * {@inheritDoc}
     */
    @Override
    public EvaluationResult getEvaluationResult() {
        return evaluationResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Evaluation deny() {
        this.evaluationResult = EvaluationResult.DENY;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Evaluation ignore() {
        this.evaluationResult = EvaluationResult.IGNORE;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Evaluation grant() {
        this.evaluationResult = EvaluationResult.GRANT;
        return this;
    }

}
