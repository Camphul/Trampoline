package com.lucadev.trampoline.security.abac.policy;

import java.util.List;

/**
 * Interface that defines methods to enforce a {@link PolicyRule}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 20-5-18
 */
public interface PolicyEnforcement {

    /**
     * Check if we have access to the given resource.
     *
     * @param subject     the identity trying to access the resource.
     * @param resource    the resource which is being accessed
     * @param action      the action that is taken against the resource
     * @param environment the current context in which the action is taking place
     * @return the result of the policy check, true means we are allowed to access and false should deny access.
     */
    boolean check(Object subject, Object resource, Object action, Object environment);

    /**
     * Check if we have access to the given resource.
     * In this method the Spring {@link org.springframework.security.core.context.SecurityContext} will be used.
     *
     * @param resource   the resource which is being accessed
     * @param permission the permission required by the action against the resource.
     */
    void check(Object resource, String permission);

    /**
     * Filter the list for access
     *
     * @param resources  the list of resources
     * @param permission the permission to check
     * @param <T>        the type of resource in the list
     * @return the filtered list.
     */
    <T> List<T> filter(List<T> resources, String permission);

}
