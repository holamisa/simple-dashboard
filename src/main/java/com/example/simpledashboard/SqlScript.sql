create database if not exists `simple_dashboard`;

create table if not exists `simple_dashboard`.`board` (
`id` bigint(32) not null auto_increment comment 'primary key',
`board_name` varchar(100) not null,
`status` varchar(50) not null,
primary key (`id`)
)
engine = InnoDB;

 create table if not exists `simple_dashboard`.`post` (
 `id` bigint(32) not null auto_increment,
 `board_id` bigint(32) not null,
 `user_name` varchar(50) not null,
 `password` varchar(20) not null,
 `email` varchar(100) not null,
 `status` varchar(50) not null,
 `title` varchar(100) not null,
 `content` text null,
 `posted_at` datetime not null,
 primary key (`id`)
 )
 engine = InnoDB;

 create table if not exists `simple_dashboard`.`reply` (
 `id` bigint(32) not null auto_increment,
 `post_id` bigint(32) not null,
 `user_name` varchar(50) not null,
 `password` varchar(20) not null,
 `status` varchar(50) not null,
 `title` varchar(100) not null,
 `content` text null,
 `replied_at` datetime not null,
 primary key (`id`)
 )
 engine = InnoDB;