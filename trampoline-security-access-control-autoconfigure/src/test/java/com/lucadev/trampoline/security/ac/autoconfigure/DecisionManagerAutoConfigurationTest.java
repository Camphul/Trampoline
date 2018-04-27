package com.lucadev.trampoline.security.ac.autoconfigure;

import com.lucadev.trampoline.security.ac.decision.AffirmativeDecisionManager;
import com.lucadev.trampoline.security.ac.decision.DecisionManager;
import com.lucadev.trampoline.security.ac.evaluate.EvaluationFactory;
import com.lucadev.trampoline.security.ac.evaluate.impl.EvaluationFactoryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
public class DecisionManagerAutoConfigurationTest {

    private AnnotationConfigApplicationContext context;

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext();
    }

    @After
    public void tearDown() throws Exception {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void registerAffirmativeDecisionManagerAutomatically() {
        this.context.registerBean(DecisionManagerAutoConfiguration.class, mock(EvaluationFactory.class), new ArrayList<>());
        this.context.refresh();
        DecisionManager dm = this.context.getBean(DecisionManager.class);
        assertThat(dm, instanceOf(AffirmativeDecisionManager.class));
    }
}