CREATE DATABASE IF NOT EXISTS `user_db` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `app_db`;

DROP TABLE IF EXISTS `t_oauth`;
CREATE TABLE `t_oauth`
(
    `uid`         bigint       NOT NULL,
    `oauth_type`  tinyint      NOT NULL COMMENT 'auth类型 1.小程序;2.公众号',
    `oauth_id`    varchar(100) NOT NULL,
    `union_id`    varchar(100) DEFAULT NULL,
    `status`      tinyint      NOT NULL,
    `create_time` datetime     NOT NULL,
    `update_time` datetime     NOT NULL,
    PRIMARY KEY (`uid`, `oauth_type`, `oauth_id`),
    UNIQUE KEY `idx_t_oauth_oauth_type_oauth_id` (`oauth_type`, `oauth_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `uid`         bigint      NOT NULL AUTO_INCREMENT,
    `username`    varchar(100) DEFAULT NULL,
    `password`    varchar(100) DEFAULT NULL,
    `secret_key`  varchar(32) NOT NULL,
    `status`      tinyint     NOT NULL,
    `create_time` datetime    NOT NULL,
    `update_time` datetime    NOT NULL,
    PRIMARY KEY (`uid`),
    UNIQUE KEY `uidx_username` (`username`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000000
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `t_user_profile`;
CREATE TABLE `t_user_profile`
(
    `uid`         bigint   NOT NULL,
    `nick_name`   varchar(100) DEFAULT NULL,
    `name`        varchar(100) DEFAULT NULL,
    `mobile`      varchar(45)  DEFAULT NULL,
    `avatar`      varchar(100) DEFAULT NULL,
    `email`       varchar(100) DEFAULT NULL,
    `birthday`    date         DEFAULT NULL,
    `address`     varchar(100) DEFAULT NULL,
    `create_time` datetime NOT NULL,
    `update_time` datetime NOT NULL,
    PRIMARY KEY (`uid`),
    UNIQUE KEY `uidx_mobile` (`mobile`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `t_cms_banner`;
CREATE TABLE `t_cms_banner` (
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `url` varchar(255) NOT NULL,
    `position` varchar(20) DEFAULT NULL COMMENT '位置',
    `target` varchar(255) DEFAULT NULL,
    `sort` int NOT NULL COMMENT '越小越靠前',
    `status` tinyint NOT NULL,
    `create_time` datetime NOT NULL,
    `update_time` datetime NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `t_cms_content_type`;
CREATE TABLE `t_cms_content_type` (
     `id` int NOT NULL AUTO_INCREMENT,
     `name` varchar(50) NOT NULL,
     `status` tinyint NOT NULL COMMENT '1、有效；2、无效',
     `create_time` datetime NOT NULL,
     `update_time` datetime NOT NULL,
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `t_cms_content`;
CREATE TABLE `t_cms_content` (
    `id` int NOT NULL AUTO_INCREMENT,
    `title` varchar(255) NOT NULL,
    `description` varchar(500) DEFAULT NULL COMMENT '摘要',
    `content`  text NOT NULL,
    `type` int NOT NULL COMMENT '分类',
    `views` int NOT NULL COMMENT '浏览数',
    `status` tinyint NOT NULL COMMENT '1、有效；2、无效；3、草稿',
    `publish_time` datetime DEFAULT NULL,
    `create_time` datetime NOT NULL,
    `update_time` datetime NOT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_publish_time` (`publish_time`),
    KEY `idx_type` (`type`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;