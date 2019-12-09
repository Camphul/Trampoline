insert into `trampoline_role` (`id`, `auditing_created_at`, `auditing_updated_at`, `name`) VALUES
(0x72f7f1e86d5e4f2ba03e611481181950, '2019-07-10 07:02:58', '2019-07-10 07:02:58', 'ROLE_USER'),
(0xe82172ec8c054c12a4bfd6d42b6cb7ea, '2019-07-10 07:02:58', '2019-07-10 07:02:58', 'ROLE_ADMIN');

insert into `trampoline_user` (`id`, `auditing_created_at`, `auditing_updated_at`, `is_credentials_expired`, `email`, `is_enabled`, `is_expired`, `last_password_reset_at`, `last_seen`, `is_locked`, `password`, `username`) VALUES
(0x44a9bd72bbbf4f77a9dac808489bc617, '2019-07-10 07:03:01', '2019-07-10 11:04:42', b'0', 'admin@example.com', b'1', b'0', '2019-07-10 07:03:01', '2019-07-10 11:04:42', b'0', '$2a$10$VjdZVJxlg29G6cdQ.9O2EeHYCdbiaqpa.xMV0RkGVCtGZrKR35SXe', 'admin');

insert into `trampoline_user_role` (`user_id`, `role_id`) VALUES
(0x44a9bd72bbbf4f77a9dac808489bc617, 0x72f7f1e86d5e4f2ba03e611481181950),
(0x44a9bd72bbbf4f77a9dac808489bc617, 0xe82172ec8c054c12a4bfd6d42b6cb7ea);
