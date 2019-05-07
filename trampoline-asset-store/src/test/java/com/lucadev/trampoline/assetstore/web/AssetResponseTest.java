package com.lucadev.trampoline.assetstore.web;

import com.lucadev.trampoline.assetstore.Asset;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3-11-18
 */
public class AssetResponseTest {

	@Test
	public void shouldSucceedFrom() {
		Asset asset = new Asset(new byte[10], null);
		AssetResponse resp = AssetResponse.from(asset);
		assertEquals(asset, resp.getAsset());
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailFromNPE() {
		AssetResponse.from(null);
	}

}