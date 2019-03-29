package com.lucadev.trampoline.assetstore.repository;

import com.lucadev.trampoline.assetstore.AssetMetaData;
import com.lucadev.trampoline.data.repository.TrampolineRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link TrampolineRepository} for {@link AssetMetaData}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 9-6-18
 */
@Repository
public interface AssetMetaDataRepository extends TrampolineRepository<AssetMetaData> {

}
