package com.lucadev.trampoline.assetstore.autoconfigure;

import com.lucadev.trampoline.assetstore.AssetStore;
import com.lucadev.trampoline.assetstore.provider.local.LocalAssetStore;
import com.lucadev.trampoline.assetstore.repository.AssetMetaDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 9-6-18
 */
@Configuration
@ConditionalOnClass(AssetStore.class)
public class AssetStoreAutoConfiguration {

    /**
     * Configure default asset store.
     *
     * @param providerType
     * @param localStorageDirectory
     * @param repository
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AssetStore.class)
    public AssetStore assetStore(@Value("${trampoline.assetstore.provider:local}") String providerType,
                                 @Value("${trampoline.assetstore.provider.local.directory:./local-asset-store/}")
                                         String localStorageDirectory, AssetMetaDataRepository repository) {
        switch (providerType) {
            case "local":
                return new LocalAssetStore(localStorageDirectory, repository);
            default:
                return new LocalAssetStore(localStorageDirectory, repository);
        }
    }

}
