package com.lucadev.trampoline.security.ac.autoconfigure;

import com.lucadev.trampoline.security.ac.AccessEvaluator;
import com.lucadev.trampoline.security.ac.decision.AffirmativeDecisionManager;
import com.lucadev.trampoline.security.ac.decision.DecisionManager;
import com.lucadev.trampoline.security.ac.evaluate.EvaluationFactory;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@Configuration
@ConditionalOnClass(DecisionManager.class)
@AllArgsConstructor
public class DecisionManagerAutoConfiguration {

    private final EvaluationFactory evaluationFactory;
    private final List<AccessEvaluator> accessEvaluatorList;

    @Bean
    @ConditionalOnMissingBean
    public DecisionManager decisionManager() {
        return new AffirmativeDecisionManager(evaluationFactory, accessEvaluatorList);
    }

}
