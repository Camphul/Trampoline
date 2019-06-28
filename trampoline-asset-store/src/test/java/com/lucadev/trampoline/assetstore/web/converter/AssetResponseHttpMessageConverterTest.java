package com.lucadev.trampoline.assetstore.web.converter;

import com.lucadev.trampoline.assetstore.Asset;
import com.lucadev.trampoline.assetstore.AssetMetaData;
import com.lucadev.trampoline.assetstore.AssetStore;
import com.lucadev.trampoline.assetstore.web.AssetResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.http.MockHttpOutputMessage;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Tests functionality of sending a binary file back to the client.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3-11-18
 */
public class AssetResponseHttpMessageConverterTest {

	private AssetResponseHttpMessageConverter conv;

	private Asset asset;

	@Before
	public void setUp() {
		conv = new AssetResponseHttpMessageConverter();
		byte[] content = "hello world".getBytes();
		AssetMetaData assetMetaData = new AssetMetaData("hello.txt", "hello.txt",
				MediaType.TEXT_PLAIN_VALUE, content.length);
		asset = new Asset(content, assetMetaData);
	}

	@After
	public void tearDown() {
		conv = null;
		asset = null;
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
	public void shouldSucceedWrite() throws IOException {
		AssetResponse assetResponse = new AssetResponse(asset);
		MockHttpOutputMessage outputMessage = new MockHttpOutputMessage();
		conv.write(assetResponse, MediaType.TEXT_PLAIN, outputMessage);
		assertEquals(asset.getMetaData().getContentType(),
				outputMessage.getHeaders().getContentType().toString());
		assertTrue(outputMessage.getHeaders().getAccept().contains(MediaType.TEXT_PLAIN));
		assertArrayEquals(asset.getData(), outputMessage.getBodyAsBytes());
	}

}