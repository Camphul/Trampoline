package com.lucadev.trampoline.assetstore;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Asset meta data
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 9-6-18
 */
@Entity
@Table(name = "TRAMPOLINE_ASSET_META_DATA")
@Getter
@NoArgsConstructor
@ToString
public class AssetMetaData extends TrampolineEntity {

    @Setter
    @Column(name = "asset_name", nullable = false)
    private String name;

    @Column(name = "original_filename", nullable = false)
    private String originalFilename;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "file_size")
    private long fileSize;

    public AssetMetaData(String name, String originalFilename, String contentType, long fileSize) {
        this.name = name;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.fileSize = fileSize;
    }
}
