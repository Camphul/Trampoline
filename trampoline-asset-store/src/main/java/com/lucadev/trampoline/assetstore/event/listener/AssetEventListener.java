package com.lucadev.trampoline.assetstore.event.listener;

import com.lucadev.trampoline.assetstore.event.AssetEvent;
import org.springframework.context.ApplicationListener;

/**
 * Interface for listening to asset events.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7/14/19
 */
public interface AssetEventListener extends ApplicationListener<AssetEvent> {

	/**
	 * Handle application event.
	 * @param event the event.
	 */
	@Override
	default void onApplicationEvent(AssetEvent event) {
		switch (event.getAction()) {
			case PUT:
				handlePut(event);
				break;
			case REMOVE:
				handleRemove(event);
				break;
			default:
				handleOther(event);
		}
	}

	/**
	 * Handles put.
	 * @param event the put event.
	 */
	void handlePut(AssetEvent event);

	/**
	 * Handles remove.
	 * @param event the remove event.
	 */
	void handleRemove(AssetEvent event);

	/**
	 * Handles other events.
	 * @param event the asset event.
	 */
	void handleOther(AssetEvent event);
}
