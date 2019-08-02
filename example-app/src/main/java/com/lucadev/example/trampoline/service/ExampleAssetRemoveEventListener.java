package com.lucadev.example.trampoline.service;

import com.lucadev.trampoline.assetstore.event.AssetEvent;
import com.lucadev.trampoline.assetstore.event.listener.AssetRemoveEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Example of assetstore events.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7/14/19
 */
@Slf4j
@Component
public class ExampleAssetRemoveEventListener implements AssetRemoveEventListener {

	@Override
	public void onApplicationEvent(AssetEvent.Remove remove) {
		log.info("Removing asset {}", remove.getAssetMetaData().getOriginalFilename());
	}

}
