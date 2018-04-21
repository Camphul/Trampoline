package com.lucadev.trampoline.data;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity for JpaTest by {@link TrampolineEntityJpaTest}
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Entity
@Table(name = "TRAMPOLINE_TEST_ENTITY")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SimpleTrampolineEntity extends TrampolineEntity {
    @Column(name = "payload", nullable = false)
    private String payload;

    public SimpleTrampolineEntity(String payload) {
        this.payload = payload;
    }
}
