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
     * Target type, where to apply this privilege on.
     */
    @Column(name = "target")
    private String target;

    /**
     * Construct a new {@code Privilege}
     *
     * @param name the {@code Privilege} identifier.
     */
    public Privilege(String name) {
        this.name = name;
    }

    /**
     * Construct a new {@code Privilege}
     *
     * @param name   the {@code Privilege} identifier.
     * @param target the type to apply the privilege on.
     */
    public Privilege(String name, String target) {
        this(name);
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Privilege)) return false;
        if (!super.equals(o)) return false;

        Privilege privilege = (Privilege) o;

        if (name != null ? !name.equals(privilege.name) : privilege.name != null) return false;
        return target != null ? target.equals(privilege.target) : privilege.target == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (target != null ? target.hashCode() : 0);
        return result;
    }
}
