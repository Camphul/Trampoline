package com.lucadev.example.trampoline.controller;

import com.lucadev.trampoline.assetstore.Asset;
import com.lucadev.trampoline.assetstore.AssetMetaData;
import com.lucadev.trampoline.assetstore.AssetMetaDataBuilder;
import com.lucadev.trampoline.assetstore.AssetStore;
import com.lucadev.trampoline.assetstore.repository.AssetMetaDataRepository;
import com.lucadev.trampoline.assetstore.web.AssetResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 9-6-18
 */
@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileStoreController {

    private final AssetStore assetStore;
    private final AssetMetaDataRepository repository;

    @PostMapping
    public
    @ResponseBody
    AssetMetaData upload(@RequestParam("file") MultipartFile file) throws IOException {
        return assetStore.put(file.getBytes(), AssetMetaDataBuilder.fromMultipartFile(file));
    }

    @GetMapping
    public List<AssetMetaData> getAllAssetMetaDatas() {
        return repository.findAll();
    }


    @GetMapping(value = "/{id}")
    public
    @ResponseBody
    AssetResponse getAsset(@PathVariable("id") UUID id) {
        Asset asset = assetStore.getAsset(id);
        return AssetResponse.from(asset);
    }

    @GetMapping("/{id}/meta")
    public AssetMetaData getAssetMetaData(@PathVariable("id") UUID id) {
        return assetStore.getAssetMetaData(id);
    }


}
