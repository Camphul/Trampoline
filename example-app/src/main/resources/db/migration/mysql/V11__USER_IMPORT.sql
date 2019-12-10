SET @userRoleId = t_generate_uuid_bin();
SET @adminRoleId = t_generate_uuid_bin();
SET @adminUserId = t_generate_uuid_bin();
insert into `trampoline_role` (`id`, `auditing_created_at`, `auditing_updated_at`, `name`) VALUES
(@userRoleId, '2019-07-10 07:02:58', '2019-07-10 07:02:58', 'ROLE_USER'),
(@adminRoleId, '2019-07-10 07:02:58', '2019-07-10 07:02:58', 'ROLE_ADMIN');

insert into `trampoline_user` (`id`, `auditing_created_at`, `auditing_updated_at`, `is_credentials_expired`, `email`, `is_enabled`, `is_expired`, `last_password_reset_at`, `last_seen`, `is_locked`, `password`, `username`) VALUES
(@adminUserId, '2019-07-10 07:03:01', '2019-07-10 11:04:42', b'0', 'admin@example.com', b'1', b'0', '2019-07-10 07:03:01', '2019-07-10 11:04:42', b'0', '$2a$10$VjdZVJxlg29G6cdQ.9O2EeHYCdbiaqpa.xMV0RkGVCtGZrKR35SXe', 'admin');

insert into `trampoline_user_role` (`user_id`, `role_id`) VALUES
(@adminUserId, @userRoleId),
(@adminUserId, @adminRoleId);
