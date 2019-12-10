package com.lucadev.trampoline.assetstore.event;

import com.lucadev.trampoline.assetstore.AssetMetaData;
import org.springframework.context.ApplicationEvent;

/**
 * Event used internally to remove data when a reference gets removed.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 12/10/19
 */
public class AssetMetaDataPreRemoveEvent extends ApplicationEvent {

	public AssetMetaDataPreRemoveEvent(AssetMetaData assetMetaData) {
		super(assetMetaData);
	}

	public AssetMetaData getAssetMetaData() {
		return (AssetMetaData) this.source;
	}
}
