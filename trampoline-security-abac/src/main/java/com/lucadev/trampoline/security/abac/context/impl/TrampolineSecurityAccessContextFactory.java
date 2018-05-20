package com.lucadev.trampoline.security.abac.context.impl;

import com.lucadev.trampoline.security.abac.context.SecurityAccessContext;
import com.lucadev.trampoline.security.abac.context.SecurityAccessContextFactory;

/**
 * Default {@link SecurityAccessContextFactory} implementation.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 20-5-18
 */
public class TrampolineSecurityAccessContextFactory implements SecurityAccessContextFactory {

    @Override
    public SecurityAccessContext create(Object subject, Object resource, Object action, Object environment) {
        return new TrampolineSecurityAccessContext(subject, resource, action, environment);
    }
}
