package com.lucadev.trampoline.security.ac.evaluate.impl;

import com.lucadev.trampoline.security.ac.evaluate.EvaluationResult;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
public class EvaluationImplTest {

    @Test
    public void shouldSucceedCreateDenyEvaluation() {
        EvaluationResult expected = EvaluationResult.DENY;
        EvaluationResult result = new EvaluationImpl(expected).getEvaluationResult();
        assertEquals(expected, result);
    }

    @Test
    public void shouldSucceedCreateIgnoreEvaluation() {
        EvaluationResult expected = EvaluationResult.IGNORE;
        EvaluationResult result = new EvaluationImpl(expected).getEvaluationResult();
        assertEquals(expected, result);
    }

    @Test
    public void shouldSucceedCreateGrantEvaluation() {
        EvaluationResult expected = EvaluationResult.GRANT;
        EvaluationResult result = new EvaluationImpl(expected).getEvaluationResult();
        assertEquals(expected, result);
    }

}