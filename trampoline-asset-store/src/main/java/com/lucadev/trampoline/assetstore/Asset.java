package com.lucadev.trampoline.assetstore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Wraps the asset input stream together with the meta data.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 9-6-18
 */
@Getter
@ToString
@AllArgsConstructor
public class Asset {

    private final byte[] data;
    private final AssetMetaData metaData;

}
