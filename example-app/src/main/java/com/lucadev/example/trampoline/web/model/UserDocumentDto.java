package com.lucadev.example.trampoline.web.model;

import com.lucadev.trampoline.security.web.model.EmbeddedUser;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 12/10/19
 */
@Data
public class UserDocumentDto {

	private UUID id;
	private Instant created;
	private EmbeddedUser author;
	private AssetMetaDataDto asset;
}
