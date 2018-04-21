package com.lucadev.trampoline.data.autoconfigure;

import com.lucadev.trampoline.data.configuration.TrampolineDataConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class TrampolineDataAutoConfigurationTest {

    private AnnotationConfigApplicationContext context;

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext();
    }

    @After
    public void tearDown() throws Exception {
        if(this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void registersTrampolineDataConfigurationAutomatically() {
        this.context.register(TrampolineDataAutoConfiguration.class);
        this.context.refresh();
        TrampolineDataConfiguration config = this.context.getBean(TrampolineDataConfiguration.class);
        assertEquals(TrampolineDataConfiguration.class, config.getClass());
    }

}