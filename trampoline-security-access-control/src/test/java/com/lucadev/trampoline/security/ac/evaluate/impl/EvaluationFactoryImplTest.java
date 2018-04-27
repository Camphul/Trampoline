package com.lucadev.trampoline.security.ac.evaluate.impl;

import com.lucadev.trampoline.security.ac.evaluate.EvaluationResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
public class EvaluationFactoryImplTest {

    private EvaluationFactoryImpl factory;

    @Before
    public void setup() {
        this.factory = new EvaluationFactoryImpl();
    }

    @After
    public void tearDown() {
        if(this.factory != null) {
            this.factory = null;
        }
    }

    @Test
    public void shouldSucceedCreateDenyEvaluation() {
        EvaluationResult expected = EvaluationResult.DENY;
        EvaluationResult result = factory.create(expected).getEvaluationResult();
        assertEquals(expected, result);
    }

    @Test
    public void shouldSucceedCreateIgnoreEvaluation() {
        EvaluationResult expected = EvaluationResult.IGNORE;
        EvaluationResult result = factory.create(expected).getEvaluationResult();
        assertEquals(expected, result);
    }

    @Test
    public void shouldSucceedCreateGrantEvaluation() {
        EvaluationResult expected = EvaluationResult.GRANT;
        EvaluationResult result = factory.create(expected).getEvaluationResult();
        assertEquals(expected, result);
    }
}