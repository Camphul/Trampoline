package com.lucadev.trampoline.assetstore.event;

import com.lucadev.trampoline.assetstore.AssetMetaData;
import com.lucadev.trampoline.assetstore.AssetStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Default implementation for {@link AssetEventPublisher}.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7/14/19
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultAssetEventPublisher implements AssetEventPublisher {

	private final ApplicationEventPublisher applicationEventPublisher;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void publishPut(AssetStore store, AssetMetaData assetMetaData) {
		AssetEvent.Put putEvent = new AssetEvent.Put(store, assetMetaData);
		publish(putEvent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void publishRemove(AssetStore store, AssetMetaData assetMetaData) {
		AssetEvent.Remove removeEvent = new AssetEvent.Remove(store, assetMetaData);
		publish(removeEvent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void publish(AssetEvent assetEvent) {
		log.debug("Publishing {} asset event.", assetEvent.getAction());
		this.applicationEventPublisher.publishEvent(assetEvent);
	}
}
