CREATE TABLE `trampoline_policy_rule` (
  `id` BINARY(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `condition_expression` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `target_expression` varchar(255) NOT NULL
);

CREATE TABLE `trampoline_privilege` (
  `id` BINARY(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `name` varchar(64) NOT NULL
);

CREATE TABLE `trampoline_role` (
  `id` BINARY(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `name` varchar(64) NOT NULL
);

CREATE TABLE `trampoline_role_privilege` (
  `role_id` BINARY(16) NOT NULL,
  `privilege_id` BINARY(16) NOT NULL
);

CREATE TABLE `trampoline_user` (
  `id` BINARY(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `is_credentials_expired` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `is_enabled` bit(1) NOT NULL,
  `is_expired` bit(1) NOT NULL,
  `last_password_reset_at` datetime NOT NULL,
  `last_seen` datetime NOT NULL,
  `is_locked` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(32) NOT NULL
);

CREATE TABLE `trampoline_user_role` (
  `user_id` BINARY(16) NOT NULL,
  `role_id` BINARY(16) NOT NULL
);

ALTER TABLE `trampoline_policy_rule`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `trampoline_policy_rule`
  ADD CONSTRAINT `UK_policy_rule_name` UNIQUE(`name`);

ALTER TABLE `trampoline_privilege`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `trampoline_privilege`
  ADD CONSTRAINT `UK_privilege_name` UNIQUE(`name`);

ALTER TABLE `trampoline_role`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `trampoline_role`
  ADD CONSTRAINT `UK_role_name` UNIQUE(`name`);

ALTER TABLE `trampoline_role_privilege`
  ADD CONSTRAINT fk_t_privilege_role FOREIGN KEY (`privilege_id`) REFERENCES `trampoline_privilege`(`id`);

ALTER TABLE `trampoline_role_privilege`
  ADD CONSTRAINT fk_t_role_privileges FOREIGN KEY (`role_id`) REFERENCES `trampoline_role`(`id`);

ALTER TABLE `trampoline_user`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `trampoline_user`
  ADD CONSTRAINT `UK_user_email` UNIQUE(`email`);

ALTER TABLE `trampoline_user`
  ADD CONSTRAINT `UK_user_username` UNIQUE(`username`);

ALTER TABLE `trampoline_user_role`
  ADD CONSTRAINT `fk_t_role_users` FOREIGN KEY (`user_id`) REFERENCES `trampoline_user`(`id`);

ALTER TABLE `trampoline_user_role`
  ADD CONSTRAINT `fk_t_user_roles` FOREIGN KEY (`role_id`) REFERENCES `trampoline_role`(`id`);;
