package com.lucadev.trampoline.assetstore.event.listener;

import com.lucadev.trampoline.assetstore.event.AssetEvent;
import org.springframework.context.ApplicationListener;

/**
 * Handle remove events.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7/14/19
 */
public interface AssetRemoveEventListener extends ApplicationListener<AssetEvent.Remove> {

}
