create table comment
(
    comment_id  int auto_increment
        primary key,
    video_id    int          null,
    user_id     int          null,
    like_num    bigint       null,
    content     varchar(300) null,
    create_time timestamp    null
);

create table history
(
    history_id int auto_increment
        primary key,
    user_id    int       null,
    video_id   int       null,
    watch_time timestamp null
);

create table relation
(
    relation_id int auto_increment
        primary key,
    user1_id    int     null,
    user2_id    int     null,
    kind        tinyint null
);

create table star
(
    star_id int auto_increment
        primary key,
    user_id int null,
    videoid int null
);

create table tag
(
    tag_id   int auto_increment
        primary key,
    tag_name varchar(50) null
);

create table tagrecord
(
    record_id int auto_increment
        primary key,
    tag_id    int null,
    video_id  int null
);

create table user
(
    user_id      int auto_increment
        primary key,
    username     varchar(20)  not null,
    password     varchar(50)  null,
    salt         varchar(20)  null,
    email        varchar(20)  null,
    headshot     varchar(300) null,
    introduction varchar(300) null
);

create table video
(
    video_id     int auto_increment
        primary key,
    video_name   varchar(50)  null,
    user_id      int          null,
    create_time  timestamp    null,
    pageshot     varchar(300) null,
    like_num     bigint       null,
    star_num     bigint       null,
    share_num    bigint       null,
    video_path   varchar(300) null,
    introduction varchar(300) null
);


