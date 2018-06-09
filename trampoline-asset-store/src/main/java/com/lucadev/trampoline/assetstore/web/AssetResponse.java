package com.lucadev.trampoline.assetstore.web;

import com.lucadev.trampoline.assetstore.Asset;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Model to use when wanting to return an asset to a web client with the correct content type.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 9-6-18
 */
@AllArgsConstructor
@Getter
@ToString
public class AssetResponse {

    private final Asset asset;

    public static AssetResponse from(Asset asset) {
        return new AssetResponse(asset);
    }


}
