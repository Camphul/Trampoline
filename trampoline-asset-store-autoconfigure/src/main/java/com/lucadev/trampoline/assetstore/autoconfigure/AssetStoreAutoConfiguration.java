package com.lucadev.trampoline.assetstore.autoconfigure;

import com.lucadev.trampoline.assetstore.AssetStore;
import com.lucadev.trampoline.assetstore.AssetStoreFactory;
import com.lucadev.trampoline.assetstore.provider.local.LocalAssetStore;
import com.lucadev.trampoline.assetstore.provider.local.LocalAssetStoreFactory;
import com.lucadev.trampoline.assetstore.repository.AssetMetaDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 9-6-18
 */
@Configuration
@ConditionalOnClass(AssetStore.class)
public class AssetStoreAutoConfiguration {

	/**
	 * Configure default asset store.
	 *
	 * @param providerType          which provider instance to use.
	 * @return a new {@link AssetStore}
	 */
	@Bean
	@ConditionalOnMissingBean(AssetStore.class)
	public AssetStore assetStore(List<AssetStoreFactory> factories, @Value("${trampoline.assetstore.provider:local}")
			String providerType) throws Exception {
		if(factories.isEmpty()) {
			throw new NullPointerException("Could not find matching factories for AssetStore");
		}

		AssetStoreFactory factory = factories.stream().filter(factoryPredicate -> factoryPredicate
				.supports(providerType)).findFirst().orElseThrow(() -> new NullPointerException("Could not find matching factory."));
		//found factory
		return factory.getObject();
	}

	/**
	 * Configure default asset store.
	 *
	 * @param localStorageDirectory local fs directory to use to store data.
	 * @param repository            the repo for asset metadata.
	 * @return a new {@link AssetStore}
	 */
	@Bean
	public LocalAssetStoreFactory localAssetStoreFactory(@Value("${trampoline.assetstore.provider.local.directory:./local-asset-store/}")
																 String localStorageDirectory, AssetMetaDataRepository repository) {
		return new LocalAssetStoreFactory(localStorageDirectory, repository);
	}

}
