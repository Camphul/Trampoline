package com.lucadev.trampoline.security.persistence.entity;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.util.*;

/**
 * A {@link TrampolineEntity} and {@link UserDetails} to easily manage users.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Entity
@Table(name = "TRAMPOLINE_USER")
@Setter
@Getter
public class User extends TrampolineEntity implements UserDetails {

	@NotBlank
	@Length(min = 3, max = 32)
	@Column(name = "username", nullable = false, unique = true, updatable = false)
	private String username;

	@Email
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@NotBlank
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "is_expired", nullable = false)
	private boolean expired = false;

	@Column(name = "is_locked", nullable = false)
	private boolean locked = false;

	@Column(name = "is_credentials_expired", nullable = false)
	private boolean credentialsExpired = false;

	@Column(name = "is_enabled", nullable = false)
	private boolean enabled = true;

	// UserDetails roles can never be null so we use eager loading for roles.
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "TRAMPOLINE_USER_ROLE",
			joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "ROLE_ID",
					referencedColumnName = "id"))
	private List<Role> roles = new ArrayList<>();

	@Column(name = "last_password_reset_at", nullable = false)
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastPasswordReset;

	@Column(name = "last_seen", nullable = false)
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastSeen;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
			role.getPrivileges().forEach(privilege -> authorities
					.add(new SimpleGrantedAuthority(privilege.getName())));
		});
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !this.expired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !this.credentialsExpired;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}

		User user = (User) o;

		return this.username != null ? this.username.equals(user.username)
				: user.username == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (this.username != null ? this.username.hashCode() : 0);
		return result;
	}

	/**
	 * Cast UserDetails to User.
	 * @param userDetails the UserDetails to cast.
	 * @return optional with the user. Empty optional if the userDetails is not an instance of User.
	 * @see UserDetails
	 */
	public static Optional<User> from(UserDetails userDetails) {
		if(userDetails instanceof User) {
			return Optional.of((User)userDetails);
		}
		return Optional.empty();
	}
}
