package com.lucadev.trampoline.security.service;

import com.lucadev.trampoline.security.model.Privilege;
import com.lucadev.trampoline.security.model.Role;

/**
 * Service for managing {@link Role} entities.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public interface RoleService {

    /**
     * Check if a certain {@link Role}exists.
     *
     * @param roleName the {@link Role#getName()} attribute.
     * @return true when a {@link Role} exists with the specified {@code roleName}
     */
    boolean exists(String roleName);

    /**
     * Find a {@link Role} by it's name.
     *
     * @param roleName the {@link Role#getName()} attribute value.
     * @return the found {@link Role} or null.
     */
    Role find(String roleName);

    /**
     * Check if a {@link Role} contains the given {@link Privilege}
     *
     * @param role      the {@link Role} to check on.
     * @param privilege the {@link Privilege} to find.
     * @return if the {@code privilege} was found in the {@code role}
     */
    boolean hasPrivilege(Role role, Privilege privilege);

    /**
     * Add a {@link Privilege} to a {@link Role}
     *
     * @param role      the {@link Role} to add the {@link Privilege} to.
     * @param privilege the {@link Privilege} to add to the {@link Role}
     * @return the updated {@link Role}
     */
    Role addPrivilege(Role role, Privilege privilege);

    /**
     * Remove a {@link Privilege} from a {@link Role}
     *
     * @param role      the {@link Role} to remove the {@link Privilege} from.
     * @param privilege the {@link Privilege} to remove from the {@link Role}
     * @return the updated {@link Role}
     */
    Role removePrivilege(Role role, Privilege privilege);

    /**
     * Create a new {@link Role}
     *
     * @param roleName the {@link Role#getName()}
     * @return the newly persisted {@link Role}
     */
    Role create(String roleName);

    /**
     * Update a {@link Role} entity.
     *
     * @param role the {@link Role} to update.
     * @return the updated/persisted {@link Role}
     */
    Role update(Role role);
}
