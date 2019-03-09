package com.lucadev.trampoline.security.abac.policy;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import com.lucadev.trampoline.security.abac.spel.SpelAttributeConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.expression.Expression;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 20-5-18
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "TRAMPOLINE_POLICY_RULE")
public class PolicyRule extends TrampolineEntity {

	@NotBlank
	@NotEmpty
    @Column(name = "name", nullable = false, unique = true)
    private String name;

	@NotBlank
	@NotEmpty
    @Column(name = "description", nullable = false)
    private String description;

    @Convert(converter = SpelAttributeConverter.class)
    @Column(name = "target_expression", nullable = false)
    private Expression target;

    @Convert(converter = SpelAttributeConverter.class)
    @Column(name = "condition_expression", nullable = false)
    private Expression condition;


    /**
     * Create a policy
     *
     * @param name        the name of the policy
     * @param description the description of what the policy does
     * @param target      the expression defining when to apply the condition(check if the action matches, etc..)
     * @param condition   the rule to apply(your logic goes here)
     */
    public PolicyRule(String name, String description, Expression target, Expression condition) {
        this(target, condition);
        this.name = name;
        this.description = description;
    }

    /**
     * Create a policy
     *
     * @param target    the expression defining when to apply the condition(check if the action matches, etc..)
     * @param condition the rule to apply(your logic goes here)
     */
    protected PolicyRule(Expression target, Expression condition) {
        super();
        this.target = target;
        this.condition = condition;
    }


}
