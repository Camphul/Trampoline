package com.lucadev.trampoline.security.persistence.entity;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * A {@link TrampolineEntity} to specify a fine-grained permission.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Entity
@Table(name = "trampoline_privilege",
		indexes = @Index(name = "idx_privilege_name", columnList = "name", unique = true))
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
public class Privilege extends TrampolineEntity {

	/**
	 * Privilege identifier.
	 */
	@NotBlank
	@Size(min = 2, max = 64)
	@EqualsAndHashCode.Include
	@Column(name = "name", unique = true, nullable = false)
	private String name;

	/**
	 * Construct a new {@code Privilege}.
	 * @param name the {@code Privilege} identifier.
	 */
	public Privilege(String name) {
		this.name = name;
	}

}
