package com.lucadev.trampoline.assetstore;

import java.util.UUID;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 9-6-18
 */
public abstract class AbstractAssetStore implements AssetStore {

    @Override
    public void remove(UUID id) {
        remove(getAssetMetaData(id));
    }

    @Override
    public Asset getAsset(UUID id) {
        return getAsset(getAssetMetaData(id));
    }
}
