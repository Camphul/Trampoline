package com.lucadev.trampoline.data;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity for JpaTest by {@link TrampolineEntityJpaTest}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
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
