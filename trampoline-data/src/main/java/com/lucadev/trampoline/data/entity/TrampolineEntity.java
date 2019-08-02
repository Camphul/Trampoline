package com.lucadev.trampoline.data.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * Entity base with UUID as primary key and two date fields for audit information.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class TrampolineEntity implements Serializable {

	/**
	 * Primary key is a {@link UUID}.
	 */
	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, nullable = false,
			columnDefinition = "BINARY(16)")
	@Setter(AccessLevel.PROTECTED)
	@EqualsAndHashCode.Include
	private UUID id;

	/**
	 * {@link Instant} of when this entity was first persisted into the data source.
	 */
	@CreatedDate
	@Column(name = "auditing_created_at", nullable = false, updatable = false)
	@PastOrPresent
	@Setter(AccessLevel.PROTECTED)
	private Instant created;

	/**
	 * {@link Instant} of when this entity was last updated in the data source.
	 */
	@LastModifiedDate
	@Column(name = "auditing_updated_at", nullable = false)
	@PastOrPresent
	private Instant updated;

}
