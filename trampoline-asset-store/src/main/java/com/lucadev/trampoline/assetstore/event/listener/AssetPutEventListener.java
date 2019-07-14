package com.lucadev.trampoline.assetstore.event.listener;

import com.lucadev.trampoline.assetstore.event.AssetEvent;
import org.springframework.context.ApplicationListener;

/**
 * Handle put events.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7/14/19
 */
public interface AssetPutEventListener extends ApplicationListener<AssetEvent.Put> {
}
