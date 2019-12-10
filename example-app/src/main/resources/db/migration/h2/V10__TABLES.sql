/* H2 migration which is not being used. */
CREATE TABLE `blogpost`
(
    `id`                  binary(16)   NOT NULL,
    `auditing_created_at` datetime     NOT NULL,
    `auditing_updated_at` datetime     NOT NULL,
    `blogpost_author`     binary(16)   NOT NULL,
    `title`               varchar(255) NOT NULL,
    `content`             varchar(255) NOT NULL,
);

CREATE TABLE `blogpost_comment`
(
    `id`                      binary(16)   NOT NULL,
    `auditing_created_at`     datetime     NOT NULL,
    `auditing_updated_at`     datetime     NOT NULL,
    `blogpost_comment_author` binary(16)   NOT NULL,
    `blogpost_id`             binary(16)   NOT NULL,
    `content`                 varchar(255) NOT NULL
);


CREATE TABLE `blogpost_comments`
(
    `blogpost_id` binary(16) NOT NULL,
    `comment_id`  binary(16) NOT NULL
);


ALTER TABLE `blogpost`
    ADD PRIMARY KEY (`id`);

ALTER TABLE `blogpost`
    ADD CONSTRAINT `FK_blogpost_author` FOREIGN KEY (`blogpost_author`) REFERENCES `trampoline_user` (`id`);

ALTER TABLE `blogpost_comment`
    ADD PRIMARY KEY (`id`);

ALTER TABLE `blogpost_comment`
    ADD CONSTRAINT `FK_blogpost_comment_author` FOREIGN KEY (`blogpost_comment_author`) REFERENCES `trampoline_user` (`id`);

ALTER TABLE `blogpost_comments`
    ADD CONSTRAINT fk_blogpost_comment_blogpost FOREIGN KEY (`comment_id`) REFERENCES `blogpost_comment` (`id`);

ALTER TABLE `blogpost_comments`
    ADD CONSTRAINT fk_blogpost_blogpost_comment FOREIGN KEY (`blogpost_id`) REFERENCES `blogpost` (`id`);
