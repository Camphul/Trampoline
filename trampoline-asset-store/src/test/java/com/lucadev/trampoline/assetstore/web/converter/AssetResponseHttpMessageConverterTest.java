package com.lucadev.trampoline.assetstore.web.converter;

import com.lucadev.trampoline.assetstore.AssetStore;
import com.lucadev.trampoline.assetstore.web.AssetResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3-11-18
 */
public class AssetResponseHttpMessageConverterTest {

    private AssetResponseHttpMessageConverter conv;

    @Before
    public void setUp() {
        conv = new AssetResponseHttpMessageConverter();
    }

    @After
    public void tearDown() {
        conv = null;
    }

    @Test
    public void shouldCanWriteTrue() {
        assertTrue(conv.canWrite(AssetResponse.class, null));
    }

    @Test
    public void shouldCanWriteFalse() {
        assertFalse(conv.canWrite(AssetStore.class, null));
    }

    @Test
    public void shouldSucceedWrite() {
        //TODO: IMP
    }
}