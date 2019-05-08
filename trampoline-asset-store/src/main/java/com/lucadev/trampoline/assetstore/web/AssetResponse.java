package com.lucadev.trampoline.assetstore.web;

import com.lucadev.trampoline.assetstore.Asset;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Model to use when wanting to return an asset to a model client with the correct content
 * type.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 9-6-18
 */
@Getter
@ToString
@AllArgsConstructor
public class AssetResponse {

	private final Asset asset;

	/**
	 * Construct {@code AssetResponse}
	 * @param asset {@link Asset} to create a model for.
	 * @return a {@link AssetResponse} instance.
	 */
	public static AssetResponse from(Asset asset) {
		if (asset == null)
			throw new NullPointerException("Cannot create model from null asset.");

		return new AssetResponse(asset);
	}

}
