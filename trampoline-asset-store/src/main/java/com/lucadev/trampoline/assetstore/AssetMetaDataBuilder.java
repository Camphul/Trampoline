package com.lucadev.trampoline.assetstore;

import org.springframework.web.multipart.MultipartFile;

/**
 * Builders for creating {@link AssetMetaData}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 9-6-18
 */
public class AssetMetaDataBuilder {

	/**
	 * Disable instance creation
	 */
	private AssetMetaDataBuilder() {
		throw new IllegalAccessError("This builder should not be constructable.");
	}

	/**
	 * Create a {@link AssetMetaData} from a multipart file.
	 *
	 * @param multipartFile the {@link MultipartFile} to create metadata from
	 * @return the {@link AssetMetaData} built from the {@code multipartFile}
	 */
	public static AssetMetaData fromMultipartFile(MultipartFile multipartFile) {
		if (multipartFile == null)
			throw new NullPointerException("Cannot create asset metadata from null multipartFile");

		return new AssetMetaData(multipartFile.getName(), multipartFile.getOriginalFilename(),
				multipartFile.getContentType(), multipartFile.getSize());
	}
}
