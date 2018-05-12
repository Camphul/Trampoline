package com.lucadev.example.trampoline.ac;

import com.lucadev.trampoline.security.ac.TrampolineAccessVoter;
import com.lucadev.trampoline.security.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 12-5-18
 */
@Service
public class DebugAccessVoter extends TrampolineAccessVoter<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebugAccessVoter.class);


    @Override
    public int vote(User user, Object o, Collection<ConfigAttribute> configAttributes) {
        LOGGER.debug("vote(user: {}, object: {}, configAttributes: {})", user.getId(), o, Arrays.toString(configAttributes.toArray()));
        return ACCESS_ABSTAIN;
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        LOGGER.debug("supports(configAttribute: {})", configAttribute);
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        LOGGER.debug("supports(class: {})", aClass.getName());
        return true;
    }
}
