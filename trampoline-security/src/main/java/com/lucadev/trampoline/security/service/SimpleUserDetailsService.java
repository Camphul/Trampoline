package com.lucadev.trampoline.security.service;

import com.lucadev.trampoline.security.authentication.SimpleUserDetails;
import com.lucadev.trampoline.security.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Simple implementation of {@link UserDetailsService}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/3/19
 */
@RequiredArgsConstructor
public class SimpleUserDetailsService implements UserDetailsService {

	private final UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userService.findByIdentificationField(username).orElseThrow(() ->
				new UsernameNotFoundException("Could not find user with username \"" + username + "\""));
		return new SimpleUserDetails(user);
	}
}
