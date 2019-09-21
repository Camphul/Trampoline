package com.lucadev.trampoline.assetstore.event;

import com.lucadev.trampoline.assetstore.AssetMetaData;
import com.lucadev.trampoline.assetstore.AssetStore;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * Event for asset store related actions.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7/14/19
 */
@Data
public class AssetEvent extends ApplicationEvent {

	private final AssetMetaData assetMetaData;

	private final Action action;

	/**
	 * Construct new asset event.
	 * @param source event source.
	 * @param assetMetaData created asset.
	 * @param action type of event.
	 */
	public AssetEvent(AssetStore source, AssetMetaData assetMetaData, Action action) {
		super(source);
		this.assetMetaData = assetMetaData;
		this.action = action;
	}

	/**
	 * Get asset store. Casted from source.
	 * @return asset store.
	 */
	public AssetStore getStore() {
		return (AssetStore) getSource();
	}

	/**
	 * Defines the action which took place.
	 */
	public enum Action {

		/**
		 * When an asset has been created.
		 */
		PUT,

		/**
		 * When as asset has been removed.
		 */
		REMOVE,

		/**
		 * When there's another action depending on implementation of asset store.
		 */
		OTHER

	}

	/**
	 * Put event.
	 */
	public static class Put extends AssetEvent {

		/**
		 * Construct new asset put event.
		 * @param source event source.
		 * @param assetMetaData asset being put.
		 */
		public Put(AssetStore source, AssetMetaData assetMetaData) {
			super(source, assetMetaData, Action.PUT);
		}

	}

	/**
	 * Remove event.
	 */
	public static class Remove extends AssetEvent {

		/**
		 * Construct new asset remove event.
		 * @param source event source.
		 * @param assetMetaData asset to be removed.
		 */
		public Remove(AssetStore source, AssetMetaData assetMetaData) {
			super(source, assetMetaData, Action.REMOVE);
		}

	}

}
