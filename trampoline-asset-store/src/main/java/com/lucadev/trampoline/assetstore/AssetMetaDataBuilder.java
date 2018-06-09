package com.lucadev.trampoline.assetstore;

import org.springframework.web.multipart.MultipartFile;

/**
 * Builders for creating {@link AssetMetaData}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 9-6-18
 */
public class AssetMetaDataBuilder {

    /**
     * Create a {@link AssetMetaData} from a multipart file.
     *
     * @param multipartFile
     * @return
     */
    public static AssetMetaData fromMultipartFile(MultipartFile multipartFile) {
        return new AssetMetaData(multipartFile.getName(), multipartFile.getOriginalFilename(),
                multipartFile.getContentType(), multipartFile.getSize());
    }
}
