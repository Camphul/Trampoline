package com.lucadev.example.trampoline.web.model.mapper;

import com.lucadev.example.trampoline.persistence.entity.UserDocument;
import com.lucadev.example.trampoline.web.model.UserDocumentDto;
import com.lucadev.trampoline.security.web.model.mapper.UserMapper;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 12/10/19
 */
@Mapper(uses = {UserMapper.class, AssetMetaDataMapper.class})
public interface UserDocumentMapper {

	UserDocumentDto toDto(UserDocument userDocument);

	List<UserDocumentDto> toDtos(Collection<UserDocument> userDocument);

}
