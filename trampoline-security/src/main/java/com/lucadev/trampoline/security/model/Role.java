package com.lucadev.trampoline.security.model;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A {@link User} group.
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Entity
@Table(name = "TRAMPOLINE_ROLE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role extends TrampolineEntity {

    /**
     * {@link User} group identifier.
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * {@code Collection} of {@link Privilege} that this group contains.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TRAMPOLINE_ROLE_PRIVILEGE",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges = new ArrayList<>();

    /**
     * Construct a new {@code Role}
     * @param name the {@code Role} identifier/name.
     */
    public Role(String name) {
        this.name = name;
    }
}
