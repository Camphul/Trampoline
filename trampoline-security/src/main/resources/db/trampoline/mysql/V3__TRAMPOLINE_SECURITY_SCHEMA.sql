CREATE TABLE trampoline_user
(
    id                     binary(16)   NOT NULL UNIQUE,
    auditing_created_at    datetime     NOT NULL,
    auditing_updated_at    datetime     NOT NULL,
    is_credentials_expired bit(1)       NOT NULL,
    email                  varchar(255) NOT NULL UNIQUE,
    is_enabled             bit(1)       NOT NULL,
    is_expired             bit(1)       NOT NULL,
    last_password_reset_at datetime     NOT NULL,
    last_seen              datetime     NOT NULL,
    is_locked              bit(1)       NOT NULL,
    password               varchar(255) NOT NULL,
    username               varchar(32)  NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX idx_user_username ON trampoline_user (username);
CREATE UNIQUE INDEX idx_user_email ON trampoline_user (email);

CREATE TABLE trampoline_role
(
    id                  binary(16)  NOT NULL UNIQUE,
    auditing_created_at datetime    NOT NULL,
    auditing_updated_at datetime    NOT NULL,
    name                varchar(64) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX idx_role_name ON trampoline_role (name);

CREATE TABLE bind_user_role
(
    user_id binary(16) NOT NULL,
    role_id binary(16) NOT NULL,
    CONSTRAINT fkb_user_id_user_role FOREIGN KEY (user_id) REFERENCES trampoline_user (id) ON DELETE CASCADE,
    CONSTRAINT fkb_role_id_user_role FOREIGN KEY (role_id) REFERENCES trampoline_role (id) ON DELETE CASCADE,
    CONSTRAINT uc_bind_user_role UNIQUE (user_id, role_id)
);

CREATE TABLE trampoline_privilege
(
    id                  binary(16)  NOT NULL UNIQUE,
    auditing_created_at datetime    NOT NULL,
    auditing_updated_at datetime    NOT NULL,
    name                varchar(64) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX idx_privilege_name ON trampoline_privilege (name);

CREATE TABLE bind_role_privilege
(
    role_id      binary(16) NOT NULL,
    privilege_id binary(16) NOT NULL,
    CONSTRAINT fkb_role_id_role_privilege FOREIGN KEY (role_id) REFERENCES trampoline_role (id) ON DELETE CASCADE,
    CONSTRAINT fkb_privilege_id_role_privilege FOREIGN KEY (privilege_id) REFERENCES trampoline_privilege (id) ON DELETE CASCADE,
    CONSTRAINT uc_bind_role_privilege UNIQUE (role_id, privilege_id)
);

CREATE TABLE trampoline_policy_rule
(
    id                   binary(16)   NOT NULL UNIQUE,
    auditing_created_at  datetime     NOT NULL,
    auditing_updated_at  datetime     NOT NULL,
    condition_expression varchar(255) NOT NULL,
    description          varchar(255) NOT NULL,
    name                 varchar(255) NOT NULL UNIQUE,
    target_expression    varchar(255) NOT NULL,
    PRIMARY KEY (id)
);
