CREATE TABLE blogpost
(
    id                  binary(16)   NOT NULL UNIQUE,
    auditing_created_at datetime     NOT NULL,
    auditing_updated_at datetime     NOT NULL,
    content             varchar(255) NOT NULL,
    title               varchar(255) NOT NULL,
    author_id           binary(16)   NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_blogpost_author_id_user_id
        FOREIGN KEY (author_id)
            REFERENCES trampoline_user (id)
            ON DELETE CASCADE
);

CREATE INDEX idx_blogpost_title ON blogpost (title);

CREATE TABLE blogpost_comment
(
    id                  binary(16)   NOT NULL UNIQUE,
    auditing_created_at datetime     NOT NULL,
    auditing_updated_at datetime     NOT NULL,
    content             varchar(255) NOT NULL,
    author_id           binary(16)   NOT NULL,
    blogpost_id         binary(16)   NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_blogpost_comment_author_id_user_id
        FOREIGN KEY (author_id) REFERENCES trampoline_user (id)
            ON DELETE CASCADE,
    CONSTRAINT fk_blogpost_comment_blogpost_id
        FOREIGN KEY (blogpost_id) REFERENCES blogpost (id)
            ON DELETE CASCADE
);

CREATE INDEX idx_blogpost_comment_blogpost_id ON blogpost_comment (blogpost_id);

CREATE TABLE bind_blogpost_blogpost_comment
(
    blogpost_id         binary(16) NOT NULL,
    blogpost_comment_id binary(16) NOT NULL,
    CONSTRAINT fkb_blogpost_id_blogpost_comment
        FOREIGN KEY (blogpost_id) REFERENCES blogpost (id),
    CONSTRAINT fkb_blogpost_comment_id_blogpost_comment
        FOREIGN KEY (blogpost_comment_id) REFERENCES blogpost_comment (id),
    CONSTRAINT uc_bind_blogpost_blogpost_comment UNIQUE (blogpost_id, blogpost_comment_id)
);

CREATE TABLE user_document
(
    id                  binary(16) NOT NULL UNIQUE,
    auditing_created_at datetime   NOT NULL,
    auditing_updated_at datetime   NOT NULL,
    author_id           binary(16) NOT NULL,
    asset_id            binary(16) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_document_author_id_user_id
        FOREIGN KEY (author_id) REFERENCES trampoline_user (id)
            ON DELETE CASCADE,
    CONSTRAINT fk_user_document_asset_id
        FOREIGN KEY (asset_id) REFERENCES trampoline_asset_meta_data (id)
            ON DELETE CASCADE,
    CONSTRAINT uc_user_document UNIQUE (author_id, asset_id)
);