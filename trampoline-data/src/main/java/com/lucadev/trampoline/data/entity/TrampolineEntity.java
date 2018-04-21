package com.lucadev.trampoline.data.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;
import java.util.UUID;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@MappedSuperclass
@Getter
public abstract class TrampolineEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @Setter(AccessLevel.PROTECTED)
    private UUID id;

    @CreatedDate
    @Column(name = "auditor_created_at", nullable = false, updatable = false)
    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @LastModifiedDate
    @Column(name = "auditor_updated_at", nullable = false)
    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
}
