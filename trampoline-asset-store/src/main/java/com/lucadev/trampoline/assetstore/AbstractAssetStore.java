package com.lucadev.trampoline.assetstore;

import java.util.UUID;

/**
 * Abstract implementation of the {@link AssetStore} interface.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 9-6-18
 */
public abstract class AbstractAssetStore implements AssetStore {

	/**
	 * Remove an asset by the {@link AssetMetaData} id.
	 * @param id the {@link Asset} id
	 * @see AssetStore#remove(UUID)
	 */
	@Override
	public void remove(UUID id) {
		remove(getAssetMetaData(id));
	}

	/**
	 * Get an asset by the {@link AssetMetaData} id.
	 * @param id the meta data id of the asset
	 * @return resolved {@link Asset} from {@link UUID}
	 * @see AssetStore#getAsset(UUID)
	 */
	@Override
	public Asset getAsset(UUID id) {
		return getAsset(getAssetMetaData(id));
	}

}
