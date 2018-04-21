package com.lucadev.trampoline.security.model;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import lombok.AccessLevel;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Entity
@Table(name = "TRAMPOLINE_USER")
@Setter
public class User extends TrampolineEntity implements UserDetails {

    @NotBlank
    @Length(min = 3, max = 20)
    @Column(name = "username", nullable = false, unique = true, updatable = false)
    @Getter
    private String username;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    @Getter
    private String email;

    @NotBlank
    @Column(name = "password", nullable = false)
    @Getter
    private String password;

    @Column(name = "is_expired", nullable = false)
    @Getter(AccessLevel.PROTECTED)
    private boolean expired = false;

    @Column(name = "is_locked", nullable = false)
    @Getter(AccessLevel.PROTECTED)
    private boolean locked = false;

    @Column(name = "is_credentials_expired", nullable = false)
    @Getter(AccessLevel.PROTECTED)
    private boolean credentialsExpired = false;

    @Column(name = "is_enabled", nullable = false)
    @Getter
    private boolean enabled = true;

    //UserDetails roles can never be null so we use eager loading for roles.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TRAMPOLINE_USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "id"))
    @Getter
    private List<Role> roles = new ArrayList<>();

    @Column(name = "last_password_reset_at", nullable = false)
    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    private Date lastPasswordReset;

    @Column(name = "last_seen", nullable = false)
    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    private Date lastSeen;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        getRoles().forEach(role -> role.getPrivileges()
                .forEach(privilege -> authorities.add(
                        new SimpleGrantedAuthority(privilege.getName()))));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }
}
