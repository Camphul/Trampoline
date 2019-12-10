package com.lucadev.example.trampoline.web.model;

import lombok.Data;

import java.util.UUID;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 12/10/19
 */
@Data
public class AssetMetaDataDto {

	private UUID id;

	private String name;

	private String originalFilename;

	private String contentType;

	private long fileSize;

}
