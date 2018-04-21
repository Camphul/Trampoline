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
@Table(name = "TRAMPOLINE_PRIVILEGE")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Privilege extends TrampolineEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public Privilege(String name) {
        this.name = name;
    }
}
