package com.lucadev.trampoline.security.service.impl;

import com.lucadev.trampoline.security.persistence.entity.Privilege;
import com.lucadev.trampoline.security.persistence.repository.PrivilegeRepository;
import com.lucadev.trampoline.security.service.PrivilegeService;
import lombok.AllArgsConstructor;

/**
 * {@link PrivilegeService} implementation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@AllArgsConstructor
public class TrampolinePrivilegeService implements PrivilegeService {

	private final PrivilegeRepository privilegeRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Privilege create(String privilege) {
		Privilege p = new Privilege(privilege);
		return this.privilegeRepository.save(p);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(String privilege) {
		this.privilegeRepository.deleteByName(privilege);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Privilege find(String privilegeName) {
		return this.privilegeRepository.findOneByName(privilegeName);
	}

}
