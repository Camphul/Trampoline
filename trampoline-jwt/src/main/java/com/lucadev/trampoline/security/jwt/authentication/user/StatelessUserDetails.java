package com.lucadev.trampoline.security.jwt.authentication.user;

import com.lucadev.trampoline.security.jwt.TokenPayload;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Stateless user details based off of {@link TokenPayload}.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/3/19
 */
public class StatelessUserDetails implements UserDetails {

	@Getter
	private final TokenPayload tokenPayload;

	public StatelessUserDetails(TokenPayload tokenPayload) {
		this.tokenPayload = tokenPayload;
	}

	/**
	 * Get principal id.
	 *
	 * @return id of the principal.
	 */
	public String getPrincipalId() {
		return this.tokenPayload.getPrincipalIdentifier();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.tokenPayload.getAuthorities();
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return this.tokenPayload.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
