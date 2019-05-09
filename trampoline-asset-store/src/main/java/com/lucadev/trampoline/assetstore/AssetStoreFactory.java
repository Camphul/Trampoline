package com.lucadev.trampoline.assetstore;

import org.springframework.beans.factory.FactoryBean;

/**
 * Interface used to decide which {@link AssetStore} implementation to use.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 5/8/19
 */
public interface AssetStoreFactory extends FactoryBean<AssetStore> {

	/**
	 * Check if we support the AssetFactory.
	 * @param identifier the asset factory identifier.
	 * @return if we support the asset store.
	 */
	boolean supports(String identifier);

}
