package com.lucadev.trampoline.security.ac.evaluator;

import com.lucadev.trampoline.security.ac.AccessEvaluator;
import com.lucadev.trampoline.security.ac.evaluate.Evaluation;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * TODO: implement something
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@Component
public class PrivilegeAccessEvaluator implements AccessEvaluator {

    @Override
    public void checkAccess(Evaluation eval, Authentication auth, Object domainObject, String targetType, String permission) {

    }
}
