package com.lucadev.trampoline.security.ac;

import com.lucadev.trampoline.security.model.User;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import java.util.Collection;

/**
 * Abstract {@link AccessDecisionVoter} to work with our security implementation.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 12-5-18
 */
public abstract class TrampolineAccessVoter<S extends Object> implements AccessDecisionVoter<S> {

    @Override
    public int vote(Authentication authentication, S s, Collection<ConfigAttribute> collection) {
        if (authentication == null || !authentication.isAuthenticated() || !supports(s.getClass())) {
            return ACCESS_ABSTAIN;
        }

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return vote(user, s, collection);
        }

        return ACCESS_ABSTAIN;
    }

    public abstract int vote(User user, S s, Collection<ConfigAttribute> configAttributes);
}
