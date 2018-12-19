package com.lucadev.trampoline.assetstore;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import lombok.*;
import org.springframework.util.unit.DataSize;

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
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

    /**
     * @return {@link DataSize} as it's preferred in the newer Spring releases.
     */
    public DataSize getDataSize() {
        return DataSize.ofBytes(getFileSize());
    }
}
