package com.lucadev.trampoline.security.abac.policy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.expression.Expression;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 20-5-18
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class PolicyRule {

    private String name;
    private String description;
    private Expression target;
    private Expression condition;


    public PolicyRule(String name, String description, Expression target, Expression condition) {
        this(target, condition);
        this.name = name;
        this.description = description;
    }

    public PolicyRule(Expression target, Expression condition) {
        super();
        this.target = target;
        this.condition = condition;
    }
}
