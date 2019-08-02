package com.lucadev.trampoline.data.entity;

import lombok.AccessLevel;
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

	/**
	 * Equals implementation which only checks the id, created date and modified date.
	 * @param o the object to compare to.
	 * @return if the objects are equal.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		TrampolineEntity that = (TrampolineEntity) o;

		if (this.id != null ? !this.id.equals(that.id) : that.id != null) {
			return false;
		}
		if (this.created != null ? !this.created.equals(that.created)
				: that.created != null) {
			return false;
		}
		return this.updated != null ? this.updated.equals(that.updated)
				: that.updated == null;

	}

	@Override
	public int hashCode() {
		int result = this.id != null ? this.id.hashCode() : 0;
		result = 31 * result + (this.created != null ? this.created.hashCode() : 0);
		result = 31 * result + (this.updated != null ? this.updated.hashCode() : 0);
		return result;
	}

}
