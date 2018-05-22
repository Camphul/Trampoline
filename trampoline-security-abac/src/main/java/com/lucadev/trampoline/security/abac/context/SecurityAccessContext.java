package com.lucadev.trampoline.security.abac.context;

import org.springframework.security.access.expression.SecurityExpressionOperations;

/**
 * Contains the context required to evaluate access
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 20-5-18
 */
public interface SecurityAccessContext extends SecurityExpressionOperations {

    /**
     * @return Identity trying to access the resource
     */
    Object getSubject();

    /**
     * @return the resource being accessed by the subject
     */
    Object getResource();

    /**
     * @return the action being taken against the resource by the subject
     */
    Object getAction();

    /**
     * @return the environment/context in which the action is being taken against the resource by the subject.
     */
    Object getEnvironment();
}
