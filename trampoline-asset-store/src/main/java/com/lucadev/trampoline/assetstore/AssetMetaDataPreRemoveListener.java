package com.lucadev.trampoline.assetstore;

import com.lucadev.trampoline.assetstore.event.AssetMetaDataPreRemoveEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

import javax.persistence.PreRemove;

/**
 * Listener used to handle asset removal(delete the file/data and not only reference).
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 12/10/19
 */
@Slf4j
@RequiredArgsConstructor
public class AssetMetaDataPreRemoveListener {

	private final ApplicationEventPublisher publisher;

	@PreRemove
	public void preRemove(AssetMetaData assetMetaData) {
		log.debug("Publishing preRemove event of asset {}", assetMetaData.getId());
		this.publisher.publishEvent(new AssetMetaDataPreRemoveEvent(assetMetaData));
	}

}
