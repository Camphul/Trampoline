package com.lucadev.trampoline.assetstore.provider.local;

import com.lucadev.trampoline.assetstore.AbstractAssetStore;
import com.lucadev.trampoline.assetstore.Asset;
import com.lucadev.trampoline.assetstore.AssetMetaData;
import com.lucadev.trampoline.assetstore.repository.AssetMetaDataRepository;
import com.lucadev.trampoline.data.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * An AssetStore implementation which operates on the local file system.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @see com.lucadev.trampoline.assetstore.AssetStore
 * @since 9-6-18
 */
@AllArgsConstructor
public class LocalAssetStore extends AbstractAssetStore {

	private final LocalAssetStoreConfigurationProperties properties;

	private final AssetMetaDataRepository repository;

	@Override
	public List<AssetMetaData> findAllByName(String name) {
		return this.repository.findByName(name);
	}

	@Override
	public List<AssetMetaData> findAllByOriginalName(String name) {
		return this.repository.findByOrOriginalFilename(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AssetMetaData put(byte[] data, AssetMetaData assetMetaData) {
		assetMetaData = this.repository.save(assetMetaData);
		FileSystemResource resource = getFileSystemResource(assetMetaData);
		try {
			resource.getOutputStream().write(data);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return assetMetaData;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Asset getAsset(AssetMetaData assetMetaData) {
		FileSystemResource resource = getFileSystemResource(assetMetaData);
		try {
			long size = assetMetaData.getFileSize();
			byte[] assetData = new byte[(int) size];

			InputStream is = resource.getInputStream();
			DataInputStream dis = new DataInputStream(is);

			dis.readFully(assetData);

			return new Asset(assetData, assetMetaData);
		}
		catch (IOException e) {
			throw new RuntimeException("Could not load asset", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(AssetMetaData assetMetaData) {
		FileSystemResource resource = getFileSystemResource(assetMetaData);
		if (resource.getFile().delete()) {
			this.repository.delete(assetMetaData);
		}
		else {
			throw new RuntimeException("Could not delete asset(no permission to fs?)");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AssetMetaData getAssetMetaData(UUID id) {
		return this.repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Could not find asset by specified id."));
	}

	/**
	 * Get the actual asset file from metadata.
	 * @param assetMetaData the metadata to obtain file info for
	 * @return file resource resolved through the metadata.
	 */
	private FileSystemResource getFileSystemResource(AssetMetaData assetMetaData) {
		UUID id = assetMetaData.getId();
		if (id == null) {
			throw new IllegalArgumentException(
					"Cannot find asset which has not been saved yet.");
		}
		return new FileSystemResource(this.properties.getDirectory() + id.toString());
	}

}
