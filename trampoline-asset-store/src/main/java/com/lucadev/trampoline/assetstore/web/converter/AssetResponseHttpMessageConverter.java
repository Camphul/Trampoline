package com.lucadev.trampoline.assetstore.web.converter;

import com.lucadev.trampoline.assetstore.Asset;
import com.lucadev.trampoline.assetstore.AssetMetaData;
import com.lucadev.trampoline.assetstore.web.AssetResponse;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

/**
 * Converts the asset to the correct content type and writes it to the response.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 9-6-18
 */
public class AssetResponseHttpMessageConverter implements HttpMessageConverter<AssetResponse> {


    @Override
    public boolean canRead(Class<?> aClass, @Nullable MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> aClass, @Nullable MediaType mediaType) {
        return AssetResponse.class.isAssignableFrom(aClass);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.singletonList(MediaType.ALL);
    }

    @Override
    public AssetResponse read(Class<? extends AssetResponse> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    /**
     * Writes the asset to the output message.
     *
     * @param assetResponse
     * @param mediaType
     * @param httpOutputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    public void write(AssetResponse assetResponse, @Nullable MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        //Get meta data
        Asset asset = assetResponse.getAsset();
        AssetMetaData metaData = asset.getMetaData();

        //Set header values
        httpOutputMessage.getHeaders()
                .setContentType(MediaType.parseMediaType(metaData.getContentType()));
        httpOutputMessage.getHeaders().setAccept(MediaType.parseMediaTypes(metaData.getContentType()));

        //Write response
        OutputStream outputStream = httpOutputMessage.getBody();
        outputStream.write(asset.getData());
        outputStream.close();
    }

}
