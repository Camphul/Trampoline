package com.lucadev.example.trampoline.web.model.mapper;

import com.lucadev.example.trampoline.web.model.AssetMetaDataDto;
import com.lucadev.trampoline.assetstore.AssetMetaData;
import org.mapstruct.Mapper;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 12/10/19
 */
@Mapper
public interface AssetMetaDataMapper {

	AssetMetaDataDto toDto(AssetMetaData assetMetaData);

}
