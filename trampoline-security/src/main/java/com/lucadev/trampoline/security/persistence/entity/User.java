package com.lucadev.trampoline.security.persistence.entity;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import com.lucadev.trampoline.data.gdpr.PersonalData;
import com.lucadev.trampoline.security.authentication.PersistentUserDetails;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * A {@link TrampolineEntity} and {@link UserDetails} to easily manage users.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Entity
@Table(name = "trampoline_user", indexes = {
		@Index(name = "idx_user_email", columnList = "email", unique = true),
		@Index(name = "idx_user_username", columnList = "username", unique = true)})
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class User extends TrampolineEntity {

	@PersonalData
	@Length(min = 2, max = 64)
	@NotBlank
	@EqualsAndHashCode.Include
	@Column(name = "username", nullable = false, unique = true, updatable = false)
	private String username;

	@Email
	@PersonalData
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
	@JoinTable(name = "bind_user_role",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id",
					nullable = false, updatable = false,
					foreignKey = @ForeignKey(name = "fkb_user_id_user_role")),
			inverseJoinColumns = @JoinColumn(name = "role_id",
					referencedColumnName = "id", nullable = false, updatable = false,
					foreignKey = @ForeignKey(name = "fkb_role_id_user_role")))
	private List<Role> roles = new ArrayList<>();

	@Column(name = "last_password_reset_at", nullable = false)
	@PastOrPresent
	private Instant lastPasswordReset;

	@Column(name = "last_seen", nullable = false)
	@PastOrPresent
	private Instant lastSeen;

	@Transient
	@Setter(AccessLevel.PRIVATE)
	private Collection<GrantedAuthority> authorities;

	/**
	 * Maps the roles and privileges to granted authorities.
	 * @return list of granted authorities for the user.
	 */
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Check if authorities was already calculated.
		if (this.authorities == null) {
			this.authorities = new ArrayList<>();
			this.getRoles().forEach(role -> {
				this.authorities.add(new SimpleGrantedAuthority(role.getName()));
				role.getPrivileges().forEach(privilege -> this.authorities
						.add(new SimpleGrantedAuthority(privilege.getName())));
			});
		}
		return this.authorities;
	}

	/**
	 * Refresh the authorities collection. Useful when roles or privileges have been
	 * modified.
	 */
	public void refreshAuthorities() {
		this.authorities = null;
		getAuthorities();
	}

	/**
	 * Return non-expiry flag.
	 * @return true when the account is not expired.
	 */
	public boolean isAccountNonExpired() {
		return !this.expired;
	}

	/**
	 * Return non-locked flag.
	 * @return true when the account is not locked.
	 */
	public boolean isAccountNonLocked() {
		return !this.locked;
	}

	/**
	 * Return credentials not expired flag.
	 * @return true when the user credentials are not expired.
	 */
	public boolean isCredentialsNonExpired() {
		return !this.credentialsExpired;
	}

	/**
	 * Cast UserDetails to User.
	 * @param userDetails the UserDetails to cast.
	 * @return optional with the user. Empty optional if the userDetails is not an
	 * instance of User.
	 * @see UserDetails
	 */
	public static Optional<User> from(UserDetails userDetails) {
		if (userDetails instanceof PersistentUserDetails) {
			return Optional.of(((PersistentUserDetails) userDetails).getUser());
		}
		return Optional.empty();
	}

}
