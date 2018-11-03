package com.lucadev.trampoline.assetstore;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3-11-18
 */
public class AssetMetaDataBuilderTest {

    @Test
    public void shouldSuccessFromMultipartFile() {
        final String NEW_FN = "23f1d2e";
        final String ORIGINAL_FN = "hello.txt";
        final byte[] CONTENT = "Hello Trampoline".getBytes();
        final String CONTENT_TYPE = "text/plain";
        MultipartFile mf = new MockMultipartFile(NEW_FN, ORIGINAL_FN, CONTENT_TYPE, CONTENT);

        AssetMetaData meta = AssetMetaDataBuilder.fromMultipartFile(mf);

        assertEquals(NEW_FN, meta.getName());
        assertEquals(ORIGINAL_FN, meta.getOriginalFilename());
        assertEquals(CONTENT_TYPE, meta.getContentType());
        assertEquals(CONTENT.length, meta.getFileSize());
        //TODO: write imp
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailFromMultipartFileNPE() {
        AssetMetaDataBuilder.fromMultipartFile(null);
    }
}