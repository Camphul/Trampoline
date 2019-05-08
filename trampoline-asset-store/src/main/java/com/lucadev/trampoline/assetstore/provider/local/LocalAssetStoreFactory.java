package com.lucadev.trampoline.assetstore.provider.local;

import com.lucadev.trampoline.assetstore.AssetStore;
import com.lucadev.trampoline.assetstore.AssetStoreFactory;
import com.lucadev.trampoline.assetstore.repository.AssetMetaDataRepository;
import org.springframework.beans.factory.annotation.Value;

/**
 * {@link AssetStoreFactory} implementation to provide {@link LocalAssetStore}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 5/8/19
 */
public class LocalAssetStoreFactory implements AssetStoreFactory {

	private final String localStorageDirectory;
	private final AssetMetaDataRepository repository;

	public LocalAssetStoreFactory(@Value("${trampoline.assetstore.provider.local.directory:./local-asset-store/}")
										  String localStorageDirectory, AssetMetaDataRepository repository) {
		this.localStorageDirectory = localStorageDirectory;
		this.repository = repository;
	}

	@Override
	public boolean supports(String identifier) {
		return "local".equalsIgnoreCase(identifier) || LocalAssetStore.class.getName().equals(identifier);
	}

	@Override
	public AssetStore getObject() throws Exception {
		return new LocalAssetStore(localStorageDirectory, repository);
	}

	@Override
	public Class<?> getObjectType() {
		return LocalAssetStore.class;
	}
}
