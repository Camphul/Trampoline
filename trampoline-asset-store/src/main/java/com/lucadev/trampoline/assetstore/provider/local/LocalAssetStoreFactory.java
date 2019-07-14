package com.lucadev.trampoline.assetstore.provider.local;

import com.lucadev.trampoline.assetstore.AssetStore;
import com.lucadev.trampoline.assetstore.AssetStoreFactory;
import com.lucadev.trampoline.assetstore.event.AssetEventPublisher;
import com.lucadev.trampoline.assetstore.repository.AssetMetaDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link AssetStoreFactory} implementation to provide {@link LocalAssetStore}.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/8/19
 */
@Slf4j
@RequiredArgsConstructor
public class LocalAssetStoreFactory implements AssetStoreFactory {

	private final LocalAssetStoreConfigurationProperties configurationProperties;

	private final AssetMetaDataRepository repository;

	private final AssetEventPublisher eventPublisher;

	@Override
	public boolean supports(String identifier) {
		return "local".equalsIgnoreCase(identifier)
				|| LocalAssetStore.class.getName().equals(identifier);
	}

	@Override
	public AssetStore getObject() throws Exception {
		log.debug("Creating new local asset store.");
		return new LocalAssetStore(this.configurationProperties, this.repository,
				this.eventPublisher);
	}

	@Override
	public Class<?> getObjectType() {
		return LocalAssetStore.class;
	}

}
