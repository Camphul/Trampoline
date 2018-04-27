package com.lucadev.example.trampoline.evaluator;

import com.lucadev.trampoline.security.ac.AccessEvaluator;
import com.lucadev.trampoline.security.ac.evaluate.Evaluation;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@AllArgsConstructor
public class BookAccessEvaluator implements AccessEvaluator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookAccessEvaluator.class);
    private final UserService userService;

    @Override
    public void checkAccess(Evaluation eval, Authentication auth, Object domainObject, String targetType, String permission) {
        LOGGER.info("checkAccess(eval={}, auth={}, domainObject={}, targetType={}, permission={})",
                eval, auth, domainObject, targetType, permission);
        if(!targetType.equals("BOOK")) {
            return;
        }
        auth.getAuthorities().forEach(System.out::println);
    }
}
