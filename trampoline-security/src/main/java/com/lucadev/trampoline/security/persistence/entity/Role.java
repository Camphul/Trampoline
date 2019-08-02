package com.lucadev.trampoline.security.persistence.entity;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Role extends TrampolineEntity {

	/**
	 * {@link User} group identifier.
	 */
	@NotBlank
	@Size(min = 2, max = 64)
	@EqualsAndHashCode.Include
	@Column(name = "name", nullable = false, unique = true)
	private String name;

	/**
	 * {@code Collection} of {@link Privilege} that this group contains.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
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

}
