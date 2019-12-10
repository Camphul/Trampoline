CREATE TABLE trampoline_asset_meta_data
(
    id                  binary(16)   NOT NULL UNIQUE,
    auditing_created_at datetime     NOT NULL,
    auditing_updated_at datetime     NOT NULL,
    content_type        varchar(255) NOT NULL,
    file_size           bigint(20)   NOT NULL,
    asset_name          varchar(255) NOT NULL,
    original_filename   varchar(255) NOT NULL,
    PRIMARY KEY (id),
    /* Filesize must always be positive */
    CHECK (file_size >= 0)
);

CREATE INDEX idx_asset_meta_data_asset_name ON trampoline_asset_meta_data (asset_name);
CREATE INDEX idx_asset_meta_data_original_filename ON trampoline_asset_meta_data (original_filename);
CREATE INDEX idx_asset_meta_data_content_type ON trampoline_asset_meta_data (content_type);
