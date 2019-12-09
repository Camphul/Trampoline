CREATE TABLE `trampoline_asset_meta_data` (
  `id` binary(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `content_type` varchar(255) DEFAULT NULL,
  `file_size` bigint(20) DEFAULT NULL,
  `asset_name` varchar(255) NOT NULL,
  `original_filename` varchar(255) NOT NULL
);

ALTER TABLE `trampoline_asset_meta_data`
  ADD PRIMARY KEY (`id`);
