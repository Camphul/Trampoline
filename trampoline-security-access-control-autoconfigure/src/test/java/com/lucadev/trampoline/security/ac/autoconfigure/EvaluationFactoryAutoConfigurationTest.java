package com.lucadev.trampoline.security.ac.autoconfigure;

import com.lucadev.trampoline.security.ac.evaluate.EvaluationFactory;
import com.lucadev.trampoline.security.ac.evaluate.impl.EvaluationFactoryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Tests autoconfiguration for {@link EvaluationFactory}
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
public class EvaluationFactoryAutoConfigurationTest {

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
    public void registersEvaluationDetailsFactoryImplAutomatically() {
        this.context.register(EvaluationFactoryAutoConfiguration.class);
        this.context.refresh();
        EvaluationFactory factory = this.context.getBean(EvaluationFactory.class);
        assertThat(factory, instanceOf(EvaluationFactoryImpl.class));
    }

}