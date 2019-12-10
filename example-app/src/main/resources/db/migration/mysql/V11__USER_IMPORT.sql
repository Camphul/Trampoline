SET @userRoleId = t_generate_uuid_bin();
SET @adminRoleId = t_generate_uuid_bin();
SET @adminUserId = t_generate_uuid_bin();
SET @userUserId = t_generate_uuid_bin();
SET @joeUserId = t_generate_uuid_bin();
INSERT INTO trampoline_role (id, auditing_created_at, auditing_updated_at, name)
VALUES (@userRoleId, '2019-07-10 07:02:58', '2019-07-10 07:02:58', 'ROLE_USER'),
       (@adminRoleId, '2019-07-10 07:02:58', '2019-07-10 07:02:58', 'ROLE_ADMIN');

INSERT INTO trampoline_user (id, auditing_created_at, auditing_updated_at, is_credentials_expired, email,
                             is_enabled, is_expired, last_password_reset_at, last_seen, is_locked,
                             password, username)
VALUES (@adminUserId, '2019-07-10 07:03:01', '2019-07-10 11:04:42', b'0', 'admin@example.com', b'1', b'0',
        '2019-07-10 07:03:01', '2019-07-10 11:04:42', b'0',
        '$2a$10$VjdZVJxlg29G6cdQ.9O2EeHYCdbiaqpa.xMV0RkGVCtGZrKR35SXe', 'admin'),
       (@userUserId, '2019-07-10 07:03:01', '2019-07-10 11:04:42', b'0', 'user@example.com', b'1', b'0',
        '2019-07-10 07:03:01', '2019-07-10 11:04:42', b'0',
        '$2a$10$VjdZVJxlg29G6cdQ.9O2EeHYCdbiaqpa.xMV0RkGVCtGZrKR35SXe', 'user'),
       (@joeUserId, '2019-07-10 07:03:01', '2019-07-10 11:04:42', b'0', 'joe@example.com', b'1', b'0',
        '2019-07-10 07:03:01', '2019-07-10 11:04:42', b'0',
        '$2a$10$VjdZVJxlg29G6cdQ.9O2EeHYCdbiaqpa.xMV0RkGVCtGZrKR35SXe', 'joe');

INSERT INTO bind_user_role (user_id, role_id)
VALUES (@adminUserId, @userRoleId),
       (@adminUserId, @adminRoleId),
       (@userUserId, @userRoleId),
       (@joeUserId, @userRoleId);
