CREATE TABLE `blogpost` (
  `id` binary(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `content` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `blogpost_author` binary(16) NOT NULL
);

CREATE TABLE `blogpost_comment` (
  `id` binary(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `content` varchar(255) NOT NULL,
  `blogpost_comment_author` binary(16) NOT NULL,
  `blogpost_id` binary(16) DEFAULT NULL
);


CREATE TABLE `blogpost_comments` (
  `blogpost_id` binary(16) NOT NULL,
  `comment_id` binary(16) NOT NULL
);

ALTER TABLE `blogpost`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_blogpost_author` (`blogpost_author`);

ALTER TABLE `blogpost_comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_blogpost_comment_author` (`blogpost_comment_author`),
  ADD KEY `fk_blogpost_comment_blogpost` (`blogpost_id`);

ALTER TABLE `blogpost_comments`
  ADD UNIQUE KEY `UK_jxj0my1uu4x2ung6qtofj6uyo` (`comment_id`),
  ADD KEY `fk_blogpost_blogpost_comment` (`blogpost_id`);
