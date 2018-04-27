package com.lucadev.trampoline.security.ac.autoconfigure;

import com.lucadev.trampoline.security.ac.evaluate.EvaluationFactory;
import com.lucadev.trampoline.security.ac.evaluate.impl.EvaluationFactoryImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfig for {@link EvaluationFactory}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@Configuration
@ConditionalOnClass(EvaluationFactory.class)
public class EvaluationFactoryAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public EvaluationFactory evaluationDetailsFactory() {
        return new EvaluationFactoryImpl();
    }

}
