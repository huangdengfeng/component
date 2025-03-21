CREATE DATABASE IF NOT EXISTS `user_db` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `user_db`;

--
-- Table structure for table `t_oauth`
--

DROP TABLE IF EXISTS `t_oauth`;
CREATE TABLE `t_oauth`
(
    `uid`         bigint       NOT NULL,
    `oauth_type`  tinyint      NOT NULL COMMENT 'auth类型',
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
;

--
-- Table structure for table `t_relation`
--

DROP TABLE IF EXISTS `t_relation`;
CREATE TABLE `t_relation`
(
    `uid`            bigint       NOT NULL,
    `relation_type`  tinyint      NOT NULL,
    `relation_value` varchar(100) NOT NULL,
    `status`         tinyint      NOT NULL,
    `create_time`    datetime     NOT NULL,
    `update_time`    datetime     NOT NULL,
    PRIMARY KEY (`uid`, `relation_type`, `relation_value`)
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
    UNIQUE KEY `uid_UNIQUE` (`uid`)
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
    PRIMARY KEY (`uid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
