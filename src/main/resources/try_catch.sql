----- DB 설정 -----
set global max_connections=1200;
set wait_timeout = 3600;

----- Create Table Query
create or replace table TRY_CATCH.badge
(
    id      bigint auto_increment
        primary key,
    title   varchar(128) not null,
    content varchar(512) null,
    img_src text         null
);

create or replace table TRY_CATCH.banner
(
    id         bigint auto_increment
        primary key,
    name       varchar(20) null,
    start_from date        null,
    end_at     date        null
);

create or replace table TRY_CATCH.bookmark
(
    id          bigint auto_increment
        primary key,
    user_id     bigint      not null,
    target_id   bigint      not null,
    target_type varchar(10) not null,
    activated   bit         null
);

create or replace table TRY_CATCH.category
(
    id   bigint auto_increment
        primary key,
    name varchar(30) null
);

create or replace table TRY_CATCH.challenge
(
    id      bigint auto_increment
        primary key,
    title   varchar(128) null,
    content varchar(512) null,
    term    int          null,
    img_src text         null
);

create or replace table TRY_CATCH.company
(
    id         bigint auto_increment
        primary key,
    name       varchar(128) null,
    name_ko    varchar(128) null,
    name_en    varchar(128) null,
    group_name varchar(128) null,
    platform   varchar(128) null,
    icon       text         null,
    logo       text         null,
    blog       text         null,
    rss        text         null,
    rss_type   varchar(10)  null
);

create or replace table TRY_CATCH.conference
(
    id         bigint auto_increment
        primary key,
    company_id bigint       not null,
    title      varchar(256) null,
    summary    varchar(256) null,
    url        text         null,
    constraint conference_ibfk_1
        foreign key (company_id) references TRY_CATCH.company (id)
);

create or replace index company_id
    on TRY_CATCH.conference (company_id);

create or replace table TRY_CATCH.feed
(
    id         bigint auto_increment
        primary key,
    company_id bigint                 not null,
    title      varchar(256)           null,
    url        text                   null,
    created_at date default curdate() null,
    view_count int  default 0         null,
    updated_at datetime               null on update current_timestamp(),
    es_id      varchar(128)           not null,
    constraint feed_ibfk_1
        foreign key (company_id) references TRY_CATCH.company (id)
);

create or replace index company_id
    on TRY_CATCH.feed (company_id);

create or replace index es_id
    on TRY_CATCH.feed (es_id);

create or replace table TRY_CATCH.github_repo
(
    id        bigint auto_increment
        primary key,
    user_id   bigint     null,
    repo_name text       null,
    do_commit tinyint(1) null,
    constraint PK_USER
        unique (user_id)
);

create or replace table TRY_CATCH.likes
(
    id          bigint auto_increment
        primary key,
    user_id     bigint      not null,
    target_id   bigint      not null,
    target_type varchar(10) not null,
    activated   bit         null
);

create or replace table TRY_CATCH.notify_type
(
    id          bigint auto_increment
        primary key,
    description varchar(128) null
);

create or replace table TRY_CATCH.notification
(
    id         bigint auto_increment
        primary key,
    user_id    bigint       null,
    target_id  bigint       null,
    typecode   bigint       null,
    created_at datetime     null,
    activated  tinyint(1)   null,
    title      varchar(128) null,
    constraint notification_notify_type_id_fk
        foreign key (typecode) references TRY_CATCH.notify_type (id)
);

create or replace table TRY_CATCH.ranking
(
    id            bigint auto_increment
        primary key,
    category_name varchar(10) null,
    score         int         null
);

create or replace table TRY_CATCH.report
(
    id          bigint auto_increment
        primary key,
    reporter    bigint      not null,
    target_type varchar(10) not null,
    target_id   bigint      not null,
    content     text        not null,
    create_at   datetime    not null
);

create or replace table TRY_CATCH.today_hot
(
    id    bigint auto_increment
        primary key,
    title varchar(80) null,
    score int         null
);

create or replace table TRY_CATCH.user
(
    id                bigint auto_increment
        primary key,
    github_node_id    varchar(50)            null,
    username          varchar(50)            null,
    git_address       varchar(50)            null,
    email             varchar(50)            null,
    activated         bit                    null,
    calendar_mail     varchar(50)            null,
    confirmation_code int                    null,
    introduction      varchar(200)           null,
    created_at        date default curdate() not null,
    points            int  default 0         not null,
    image_src         varchar(100)           null,
    company_id        bigint                 null,
    constraint user_company_id_fk
        foreign key (company_id) references TRY_CATCH.company (id)
);

create or replace table TRY_CATCH.follow
(
    id          bigint auto_increment
        primary key,
    follower_id bigint not null,
    followee_id bigint not null,
    constraint follow_ibfk_1
        foreign key (follower_id) references TRY_CATCH.user (id),
    constraint follow_ibfk_2
        foreign key (followee_id) references TRY_CATCH.user (id)
);

create or replace index followee_id
    on TRY_CATCH.follow (followee_id);

create or replace index follower_id
    on TRY_CATCH.follow (follower_id);

create or replace table TRY_CATCH.history
(
    id      bigint auto_increment
        primary key,
    user_id bigint not null,
    year    int    null,
    month   int    null,
    weight  int    null,
    constraint history_ibfk_1
        foreign key (user_id) references TRY_CATCH.user (id)
);

create or replace index user_id
    on TRY_CATCH.history (user_id);

create or replace table TRY_CATCH.my_badge
(
    id          bigint auto_increment
        primary key,
    badge_id    bigint      not null,
    user_id     bigint      not null,
    status_info varchar(10) null,
    on_profile  tinyint(1)  null,
    earned_at   datetime    not null,
    constraint my_badge_ibfk_1
        foreign key (user_id) references TRY_CATCH.user (id),
    constraint my_badge_ibfk_2
        foreign key (badge_id) references TRY_CATCH.badge (id)
);

create or replace index badge_id
    on TRY_CATCH.my_badge (badge_id);

create or replace index user_id
    on TRY_CATCH.my_badge (user_id);

create or replace table TRY_CATCH.my_challenge
(
    id           bigint auto_increment
        primary key,
    challenge_id bigint                               not null,
    user_id      bigint                               not null,
    progress     mediumtext                           null,
    status_info  varchar(10)                          null,
    start_from   datetime default current_timestamp() null,
    end_at       datetime                             null,
    earned_at    datetime                             null,
    constraint my_challenge_ibfk_1
        foreign key (challenge_id) references TRY_CATCH.challenge (id),
    constraint my_challenge_ibfk_2
        foreign key (user_id) references TRY_CATCH.user (id)
);

create or replace index challenge_id
    on TRY_CATCH.my_challenge (challenge_id);

create or replace index user_id
    on TRY_CATCH.my_challenge (user_id);

create or replace table TRY_CATCH.question
(
    id            bigint auto_increment
        primary key,
    category_name varchar(10)                    null,
    user_id       bigint                         not null,
    title         varchar(128)                   null,
    content       text                           null,
    error_code    text                           null,
    created_at    datetime     default curdate() null,
    updated_at    datetime                       null on update current_timestamp(),
    chosen        tinyint(1)                     null,
    view_count    int                            null,
    likes         int                            null,
    hidden        tinyint(1)                     null,
    tags          varchar(200) default ''        null,
    constraint question_ibfk_2
        foreign key (user_id) references TRY_CATCH.user (id)
);

create or replace table TRY_CATCH.answer
(
    id          bigint auto_increment
        primary key,
    question_id bigint                     not null,
    user_id     bigint                     not null,
    content     text                       null,
    created_at  datetime default curdate() null,
    updated_at  datetime                   null on update current_timestamp(),
    chosen      tinyint(1)                 null,
    likes       int                        null,
    hidden      tinyint(1)                 null,
    constraint answer_ibfk_1
        foreign key (question_id) references TRY_CATCH.question (id),
    constraint answer_ibfk_2
        foreign key (user_id) references TRY_CATCH.user (id)
);

create or replace index question_id
    on TRY_CATCH.answer (question_id);

create or replace index user_id
    on TRY_CATCH.answer (user_id);

create or replace index user_id
    on TRY_CATCH.question (user_id);

create or replace table TRY_CATCH.`read`
(
    id      bigint auto_increment
        primary key,
    feed_id bigint                     not null,
    user_id bigint                     not null,
    read_at datetime default curdate() not null,
    constraint read_ibfk_1
        foreign key (feed_id) references TRY_CATCH.feed (id),
    constraint read_ibfk_2
        foreign key (user_id) references TRY_CATCH.user (id)
);

create or replace index feed_id
    on TRY_CATCH.`read` (feed_id);

create or replace index user_id
    on TRY_CATCH.`read` (user_id);

create or replace table TRY_CATCH.roadmap
(
    id         bigint auto_increment
        primary key,
    user_id    bigint                     null,
    node       text                       null,
    edge       text                       null,
    tag        varchar(50)                null,
    title      varchar(50)                null,
    created_at datetime default curdate() null,
    updated_at datetime default curdate() null on update current_timestamp(),
    likes      int      default 0         null,
    constraint roadmap_pk
        unique (user_id),
    constraint roadmap_user_id_fk
        foreign key (user_id) references TRY_CATCH.user (id)
);

create or replace table TRY_CATCH.subscription
(
    id         bigint auto_increment
        primary key,
    user_id    bigint not null,
    company_id bigint not null,
    constraint subscription_ibfk_1
        foreign key (user_id) references TRY_CATCH.user (id),
    constraint subscription_ibfk_2
        foreign key (company_id) references TRY_CATCH.company (id)
);

create or replace index company_id
    on TRY_CATCH.subscription (company_id);

create or replace index user_id
    on TRY_CATCH.subscription (user_id);

create or replace table TRY_CATCH.withdrawal
(
    id     bigint auto_increment
        primary key,
    reason varchar(100) not null
);

insert into notify_type
values (1, follow),
       (2, answerAcceptance),
       (3, answerRegistration);
