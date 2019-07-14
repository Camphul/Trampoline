package com.lucadev.trampoline.security.persistence.entity;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A {@link User} group.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Entity
@Table(name = "TRAMPOLINE_ROLE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role extends TrampolineEntity {

	/**
	 * {@link User} group identifier.
	 */
	@NotBlank
	@Size(min = 2, max = 64)
	@Column(name = "name", nullable = false, unique = true)
	private String name;

	/**
	 * {@code Collection} of {@link Privilege} that this group contains.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "TRAMPOLINE_ROLE_PRIVILEGE",
			joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "privilege_id",
					referencedColumnName = "id"))
	private Collection<Privilege> privileges = new ArrayList<>();

	/**
	 * Construct a new {@code Role}.
	 * @param name the {@code Role} identifier/name.
	 */
	public Role(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Role)) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}

		Role role = (Role) o;

		if (this.name != null ? !this.name.equals(role.name) : role.name != null) {
			return false;
		}
		return this.privileges != null ? this.privileges.equals(role.privileges)
				: role.privileges == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
		result = 31 * result + (this.privileges != null ? this.privileges.hashCode() : 0);
		return result;
	}

}
