package com.lucadev.trampoline.data.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;
import java.util.UUID;

/**
 * Entity base with UUID as primary key and two date fields for audit information
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Getter
@Setter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class TrampolineEntity {

    /**
     * Primary key is a {@link UUID}
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @Setter(AccessLevel.PROTECTED)
    private UUID id;

    /**
     * {@link Date} of when this entity was first persisted into the data source.
     */
    @CreatedDate
    @Column(name = "auditing_created_at", nullable = false, updatable = false)
    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    @Setter(AccessLevel.PROTECTED)
    private Date created;

    /**
     * {@link Date} of when this entity was last updated in the data source.
     */
    @LastModifiedDate
    @Column(name = "auditing_updated_at", nullable = false)
    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    /**
     * Check if the entity has not been persisted yet.
     *
     * @return true if the id is null(which means it has not been persisted yet)
     */
    public boolean isNewTrampolineEntity() {
        return this.id == null;
    }

    /**
     * Equals implementation which only checks the id, created date and modified date.
     *
     * @param o the object to compare to.
     * @return if the objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrampolineEntity that = (TrampolineEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        return updated != null ? updated.equals(that.updated) : that.updated == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        return result;
    }
}
