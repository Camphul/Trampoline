package com.lucadev.trampoline.security.model;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
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
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TRAMPOLINE_ROLE_PRIVILEGE",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges = new ArrayList<>();

    public Role(String name) {
        this.name = name;
    }
}
