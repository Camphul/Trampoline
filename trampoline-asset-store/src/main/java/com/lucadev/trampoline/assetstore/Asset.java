package com.lucadev.trampoline.assetstore;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Wraps the asset input stream together with the meta data.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 9-6-18
 */
@AllArgsConstructor
@Getter
public class Asset {

    private final byte[] data;
    private final AssetMetaData metaData;

    @Override
    public String toString() {
        return "Asset{" +
                "metaData=" + metaData +
                '}';
    }
}
