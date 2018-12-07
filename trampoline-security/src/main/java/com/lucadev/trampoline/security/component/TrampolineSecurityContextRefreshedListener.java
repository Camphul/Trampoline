package com.lucadev.trampoline.security.component;

import com.lucadev.trampoline.security.configuration.AuthorizationSchemeBuilderConfiguration;
import com.lucadev.trampoline.security.service.AuthorizationSchemeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@AllArgsConstructor
@Component
public class TrampolineSecurityContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    /**
     * Order on which listener gets invoked first.
     */
    public static final int TRAMPOLINE_SECURITY_CONTEXT_REFRESHED_LISTENER_ORDER = 10;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrampolineSecurityContextRefreshedListener.class);

    private final AuthorizationSchemeBuilderConfiguration authorizationSchemeBuilderConfiguration;
    private final AuthorizationSchemeService authorizationSchemeService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        LOGGER.debug("Invoking authorization scheme configuration.");
        authorizationSchemeBuilderConfiguration.build(authorizationSchemeService.builder());
    }

    @Override
    public int getOrder() {
        return TRAMPOLINE_SECURITY_CONTEXT_REFRESHED_LISTENER_ORDER;
    }
}
