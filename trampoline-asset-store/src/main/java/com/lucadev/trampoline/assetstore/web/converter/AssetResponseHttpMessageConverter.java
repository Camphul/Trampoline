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
 * Converts the asset to the correct content type and writes it to the model(instead of plaintext/html)
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 9-6-18
 */
public class AssetResponseHttpMessageConverter implements HttpMessageConverter<AssetResponse> {

    /**
     * @param aClass the class to test support for.
     * @param mediaType the media type of the incoming request
     * @return if we support reading it.
     * @see HttpMessageConverter#canRead(Class, MediaType)
     */
    @Override
    public boolean canRead(Class<?> aClass, @Nullable MediaType mediaType) {
        return false;
    }

    /**
     * @param aClass if we can write a response for aClass.
     * @param mediaType the media type.
     * @return if we can write the response.
     * @see HttpMessageConverter#canWrite(Class, MediaType)
     */
    @Override
    public boolean canWrite(Class<?> aClass, @Nullable MediaType mediaType) {
        return AssetResponse.class.isAssignableFrom(aClass);
    }

    /**
     * @return supported media types.
     * @see HttpMessageConverter#getSupportedMediaTypes()
     */
    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.singletonList(MediaType.ALL);
    }

    /**
     * @param aClass read a response from class.
     * @param httpInputMessage the input.
     * @return a response
     * @throws IOException possible exception
     * @throws HttpMessageNotReadableException possible exception
     * @see HttpMessageConverter#read(Class, HttpInputMessage)
     */
    @Override
    public AssetResponse read(Class<? extends AssetResponse> aClass, HttpInputMessage httpInputMessage) throws IOException {
        return null;
    }

    /**
     * Writes the asset to the output message.
     *
     * @param assetResponse response to write.
     * @param mediaType mediatype to use
     * @param httpOutputMessage the final output message containing the asset.
     * @throws IOException a possible exception.
     * @throws HttpMessageNotWritableException if we failed to write a response.
     * @see HttpMessageConverter#write(Object, MediaType, HttpOutputMessage)
     */
    @Override
    public void write(AssetResponse assetResponse, @Nullable MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException {
        //Get meta data
        Asset asset = assetResponse.getAsset();
        AssetMetaData metaData = asset.getMetaData();

        //Set header values
        httpOutputMessage.getHeaders()
                .setContentType(MediaType.parseMediaType(metaData.getContentType()));
        httpOutputMessage.getHeaders()
                .setAccept(MediaType.parseMediaTypes(metaData.getContentType()));

        //Write model
        OutputStream outputStream = httpOutputMessage.getBody();
        outputStream.write(asset.getData());
        outputStream.close();
    }

}
