
CREATE TABLE `challenge`
(
    `id`         bigint       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `condition`  varchar(100) NULL,
    `name`       varchar(30)  NULL,
    `start_from` date         NULL,
    `end_at`     date         NULL
);

CREATE TABLE `company`
(
    `id`   bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(50) NULL
);

CREATE TABLE `roadmap`
(
    `id`      bigint   NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `context` LONGTEXT NULL
);

CREATE TABLE `badge`
(
    `id`        bigint       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`      varchar(50)  NOT NULL,
    `condition` varchar(100) NULL
);

CREATE TABLE `today_hot`
(
    `id`    bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `title` varchar(80) NULL,
    `score` int         NULL
);

CREATE TABLE `category`
(
    `id`   bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(30) NULL
);

CREATE TABLE `banner`
(
    `id`         bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`       varchar(20) NULL,
    `start_from` date        NULL,
    `end_at`     date        NULL
);

CREATE TABLE `withdrawal`
(
    `id`     bigint       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `reason` varchar(100) NOT NULL
);

CREATE TABLE `report`
(
    `id`          bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `reporter`    bigint      NOT NULL,
    `target_type` VARCHAR(10) NOT NULL,
    `target_id`   bigint      NOT NULL,
    `content`     text        NOT NULL,
    `create_at`   datetime    NOT NULL
);

CREATE TABLE `user`
(
    `id`                bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `github_node_id`    varchar(50) NULL,
    `username`          varchar(50) NULL,
    `git_address`       varchar(50) NULL,
    `activated`         boolean     NULL,
    `email`             varchar(50) NULL,
    `calendar_mail`     varchar(50) NULL,
    `confirmation_code` int         NULL,
    `confirmed`         boolean     NOT NULL DEFAULT FALSE,
    `created_at`        date        NOT NULL DEFAULT current_date(),
    `points`            int         NOT NULL DEFAULT 0
);

CREATE TABLE `question`
(
    `id`          bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `category_id` bigint      NOT NULL REFERENCES category (id),
    `user_id`     bigint      NOT NULL REFERENCES user (id),
    `title`       varchar(50) NULL,
    `content`     text        NULL,
    `error_code`  text        NULL,
    `created_at`  date        NULL DEFAULT current_date(),
    `updated_at`  datetime    NULL ON UPDATE current_timestamp(),
    `chosen`      boolean     NULL,
    `view_count`  int         NULL,
    `likes`       int         NULL,
    `hidden`      boolean     NULL
);

CREATE TABLE `feed`
(
    `id`         bigint       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `company_id` bigint       NOT NULL REFERENCES company (id),
    `title`      varchar(80)  NULL,
    `url`        varchar(100) NULL,
    `created_at` date         NULL DEFAULT current_date(),
    `view_count` int          NULL DEFAULT 0,
    `updated_at` datetime     NULL ON UPDATE current_timestamp()
);


CREATE TABLE `my_badge`
(
    `id`             bigint   NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `user_id`        bigint   NOT NULL REFERENCES user (id),
    `badge_id`       bigint   NOT NULL REFERENCES badge (id),
    `representation` boolean  NULL,
    `earn`           datetime NOT NULL
);

CREATE TABLE `answer`
(
    `id`          bigint  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `question_id` bigint  NOT NULL REFERENCES question (id),
    `user_id`     bigint  NOT NULL REFERENCES user (id),
    `content`     text    NULL,
    `created_at`  date    NULL DEFAULT current_date(),
    `chosen`      boolean NULL,
    `likes`       int     NULL,
    `hidden`      boolean NULL
);

CREATE TABLE `follow`
(
    `id`          bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `follower_id` bigint NOT NULL REFERENCES user (id),
    `followee_id` bigint NOT NULL REFERENCES user (id)
);



CREATE TABLE `history`
(
    `id`      bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `user_id` bigint NOT NULL REFERENCES user (id),
    `year`    int    NULL,
    `month`   int    NULL,
    `weight`  int    NULL
);



CREATE TABLE `conference`
(
    `id`         bigint       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `company_id` bigint       NOT NULL REFERENCES company (id),
    `title`      varchar(80)  NULL,
    `summary`    varchar(200) NULL,
    `url`        varchar(100) NULL
);



CREATE TABLE `my_challenge`
(
    `id`           bigint   NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `challenge_id` bigint   NOT NULL REFERENCES challenge (id),
    `user_id`      bigint   NOT NULL REFERENCES user (id),
    `progress`     int      NULL,
    `succeed`      boolean  NULL,
    `earn`         datetime NULL
);

CREATE TABLE `subscription`
(
    `id`         bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `user_id`    bigint NOT NULL REFERENCES user (id),
    `company_id` bigint NOT NULL REFERENCES company (id)
);

CREATE TABLE `read`
(
    `id`       bigint  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `feed_id`  bigint  NOT NULL REFERENCES feed (id),
    `user_id`  bigint  NOT NULL REFERENCES user (id),
    `bookmark` boolean NULL
);



CREATE TABLE `ranking`
(
    `id`          bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `category_id` bigint NOT NULL REFERENCES category (id),
    `score`       int    NULL
);

CREATE TABLE `challenge_group`
(
    `id`           bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `challenge_id` bigint NOT NULL REFERENCES challenge (id),
    `user_id`      bigint NOT NULL REFERENCES user (id)
);

