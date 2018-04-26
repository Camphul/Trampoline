package com.lucadev.trampoline.security.model;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * A {@link TrampolineEntity} to specify a fine-grained permission.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Entity
@Table(name = "TRAMPOLINE_PRIVILEGE")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Privilege extends TrampolineEntity {

    /**
     * Privilege identifier.
     */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /**
     * Construct a new {@code Privilege}
     * @param name the {@code Privilege} identifier.
     */
    public Privilege(String name) {
        this.name = name;
    }
}
