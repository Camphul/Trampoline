package com.lucadev.trampoline.assetstore;

import java.util.UUID;

/**
 * Interface that defines methods required to handle storage needs(crud file operations)
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 9-6-18
 */
public interface AssetStore {

    /**
     * Store the data
     *
     * @param data          the raw binary data
     * @param assetMetaData the meta data
     * @return the updated assetMetaData
     */
    AssetMetaData put(byte[] data, AssetMetaData assetMetaData);

    /**
     * Get asset
     *
     * @param assetMetaData the meta data of the asset
     * @return the asset
     */
    Asset getAsset(AssetMetaData assetMetaData);

    /**
     * Get asset
     *
     * @param id the meta data id of the asset
     * @return the asset
     */
    Asset getAsset(UUID id);

    /**
     * Remove asset
     *
     * @param assetMetaData
     */
    void remove(AssetMetaData assetMetaData);

    /**
     * Remove asset
     *
     * @param id
     */
    void remove(UUID id);

    /**
     * Get asset meta data
     *
     * @param id
     * @return
     */
    AssetMetaData getAssetMetaData(UUID id);

}
