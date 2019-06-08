package com.lucadev.trampoline.assetstore.provider.local;

import com.lucadev.trampoline.assetstore.AssetStore;
import com.lucadev.trampoline.assetstore.repository.AssetMetaDataRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Test factory.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
public class LocalAssetStoreFactoryTest {

	private LocalAssetStoreFactory assetStoreFactory;

	private AssetMetaDataRepository mockedRepository;

	private String storeDir = "_junit_test";

	@Before
	public void setUp() throws Exception {
		mockedRepository = mock(AssetMetaDataRepository.class);
		assetStoreFactory = new LocalAssetStoreFactory(storeDir, mockedRepository);
	}

	@After
	public void tearDown() throws Exception {
		mockedRepository = null;
		assetStoreFactory = null;
	}

	@Test
	public void shouldSucceedSupports_local() {
		assertTrue("Factory should support identifier.",
				assetStoreFactory.supports("local"));
	}

	@Test
	public void shouldSucceedSupports_className() {
		assertTrue("Factory should support identifier.",
				assetStoreFactory.supports(LocalAssetStore.class.getName()));
	}

	@Test
	public void shouldSucceedGetObject() throws Exception {
		AssetStore assetStore = assetStoreFactory.getObject();
		assertTrue(assetStore instanceof LocalAssetStore);
	}

	@Test
	public void getObjectType() {
		assertTrue(LocalAssetStore.class.equals(assetStoreFactory.getObjectType()));
	}

}