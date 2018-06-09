package com.lucadev.trampoline.assetstore.provider.local;

import com.lucadev.trampoline.assetstore.AbstractAssetStore;
import com.lucadev.trampoline.assetstore.Asset;
import com.lucadev.trampoline.assetstore.AssetMetaData;
import com.lucadev.trampoline.assetstore.repository.AssetMetaDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * A {@link com.lucadev.trampoline.assetstore.AssetStore} implementation which operates on the local file system.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 9-6-18
 */
@AllArgsConstructor
public class LocalAssetStore extends AbstractAssetStore {

    private final String storageDirectory;
    private final AssetMetaDataRepository repository;

    @Override
    public AssetMetaData put(byte[] data, AssetMetaData assetMetaData) {
        assetMetaData = repository.save(assetMetaData);
        FileSystemResource resource = getFileSystemResource(assetMetaData);
        try {
            resource.getOutputStream().write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return assetMetaData;
    }

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
        } catch (IOException e) {
            throw new RuntimeException("Could not load asset", e);
        }
    }

    @Override
    public void remove(AssetMetaData assetMetaData) {
        FileSystemResource resource = getFileSystemResource(assetMetaData);
        if (resource.getFile().delete()) {
            repository.delete(assetMetaData);
        } else {
            throw new RuntimeException("Could not delete asset");
        }
    }

    @Override
    public AssetMetaData getAssetMetaData(UUID id) {
        return repository.findById(id).orElse(null);
    }

    private FileSystemResource getFileSystemResource(AssetMetaData assetMetaData) {
        UUID id = assetMetaData.getId();
        if (id == null) {
            throw new IllegalArgumentException("Cannot find asset which has not been saved yet.");
        }
        return new FileSystemResource(storageDirectory + id.toString());
    }
}
