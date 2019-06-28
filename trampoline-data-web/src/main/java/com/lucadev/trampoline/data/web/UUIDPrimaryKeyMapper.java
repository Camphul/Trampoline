package com.lucadev.trampoline.data.web;

import java.util.UUID;

/**
 * UUID implementation for {@link PrimaryKeyMapper}.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/28/19
 * @see UUID
 */
public class UUIDPrimaryKeyMapper implements PrimaryKeyMapper<UUID> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<UUID> getPrimaryKeyType() {
		return UUID.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UUID getPrimaryKey(String primaryKey) {
		return UUID.fromString(primaryKey);
	}

}
