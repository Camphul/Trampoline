package com.lucadev.trampoline.assetstore.event;

import com.lucadev.trampoline.assetstore.AssetMetaData;
import com.lucadev.trampoline.assetstore.AssetStore;

/**
 * Publisher interface for asset events.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7/14/19
 */
public interface AssetEventPublisher {

	/**
	 * Publish asset put event.
	 * @param store store from which it is put.
	 * @param assetMetaData created asset metadata.
	 */
	void publishPut(AssetStore store, AssetMetaData assetMetaData);

	/**
	 * Publish asset remove event.
	 * @param store store from which it is removed.
	 * @param assetMetaData asset to be removed.
	 */
	void publishRemove(AssetStore store, AssetMetaData assetMetaData);

	/**
	 * Publish asset event.
	 * @param assetEvent event to publish.
	 */
	void publish(AssetEvent assetEvent);
}
