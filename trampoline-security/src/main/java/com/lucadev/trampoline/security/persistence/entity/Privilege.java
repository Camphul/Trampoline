package com.lucadev.trampoline.security.persistence.entity;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * A {@link TrampolineEntity} to specify a fine-grained permission.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
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
	@NotBlank
	@Size(min = 2, max = 64)
	@Column(name = "name", unique = true, nullable = false)
	private String name;

	/**
	 * Construct a new {@code Privilege}
	 * @param name the {@code Privilege} identifier.
	 */
	public Privilege(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Privilege))
			return false;
		if (!super.equals(o))
			return false;
		Privilege privilege = (Privilege) o;
		return Objects.equals(name, privilege.name);
	}

	@Override
	public int hashCode() {

		return Objects.hash(super.hashCode(), name);
	}

}
