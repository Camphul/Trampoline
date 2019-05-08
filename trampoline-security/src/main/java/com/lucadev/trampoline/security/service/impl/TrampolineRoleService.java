package com.lucadev.trampoline.security.service.impl;

import com.lucadev.trampoline.security.persistence.entity.Privilege;
import com.lucadev.trampoline.security.persistence.entity.Role;
import com.lucadev.trampoline.security.persistence.repository.RoleRepository;
import com.lucadev.trampoline.security.service.RoleService;

/**
 * {@link RoleService} implementation
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class TrampolineRoleService implements RoleService {

	private final RoleRepository roleRepository;

	public TrampolineRoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists(String roleName) {
		return find(roleName) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role find(String roleName) {
		if (roleName == null || roleName.isEmpty()) {
			return null;
		}
		return roleRepository.findOneByName(roleName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasPrivilege(Role role, Privilege privilege) {
		if (role == null || privilege == null) {
			return false;
		}
		return role.getPrivileges().stream()
				.anyMatch(p -> p.getName().equals(privilege.getName()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role addPrivilege(Role role, Privilege privilege) {
		role.getPrivileges().add(privilege);
		return roleRepository.save(role);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role removePrivilege(Role role, Privilege privilege) {
		role.getPrivileges().remove(privilege);
		return roleRepository.save(role);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role create(String roleName) {
		Role role = new Role(roleName);
		return roleRepository.save(role);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role update(Role role) {
		return roleRepository.save(role);
	}

}
