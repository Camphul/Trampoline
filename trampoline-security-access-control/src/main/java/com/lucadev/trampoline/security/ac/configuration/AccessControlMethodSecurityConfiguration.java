package com.lucadev.trampoline.security.ac.configuration;

import com.lucadev.trampoline.security.ac.TrampolineAccessVoter;
import com.lucadev.trampoline.security.ac.TrampolinePermissionEvaluator;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Required configuration for access control
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class AccessControlMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    private final List<TrampolineAccessVoter<? extends Object>> accessVoters;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(permissionEvaluator());
        return expressionHandler;
    }

    @ConditionalOnMissingBean
    @Bean(name = "accessControlPermissionEvaluator")
    public PermissionEvaluator permissionEvaluator() {
        return new TrampolinePermissionEvaluator(aclDecisionManager());
    }

    @Bean
    public AccessDecisionManager aclDecisionManager() {
        return new AffirmativeBased(accessVoters.stream().map(v -> (AccessDecisionVoter<?>) v).collect(Collectors.toList()));
    }

}
