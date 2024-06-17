create database universe;
use universe;




create table user
(
    id           bigint auto_increment comment '用户ID'
        primary key,
    username     varchar(256)                       null comment '用户昵称',
    userAccount  varchar(512)                       not null comment '用户账号',
    avatarUrl    varchar(512)                       null comment '头像',
    gender       tinyint  default 0                 null comment '性别',
    userPassword varchar(512)                       not null comment '用户密码',
    phone        varchar(256)                       null comment '电话',
    email        varchar(512)                       null comment '邮箱',
    userStatus   int      default 0                 null comment '用户状态 0-正常',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '逻辑删除 0-正常 1-已删除',
    userRole     int      default 0                 not null comment '用户权限 0 -普通用户 1 -管理员',
    planetCode   varchar(512)                       null comment '星球编号',
    constraint user_pk_2
        unique (userAccount),
    constraint user_pk_3
        unique (planetCode)
)
    comment '用户表';
