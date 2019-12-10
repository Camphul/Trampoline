package com.lucadev.trampoline.assetstore.event.listener;

import com.lucadev.trampoline.assetstore.event.AssetMetaDataPreRemoveEvent;
import org.springframework.context.ApplicationListener;

/**
 * Event which gets triggered when an asset is about to be deleted.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 12/10/19
 */
public interface AssetMetaDataPreRemoveEventListener
		extends ApplicationListener<AssetMetaDataPreRemoveEvent> {

}
