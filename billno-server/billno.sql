CREATE DATABASE IF NOT EXISTS `billno_db` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `billno_db`;

DROP TABLE IF EXISTS `t_billno`;
CREATE TABLE `t_billno` (
  `biz_code` varchar(100) NOT NULL COMMENT '业务编号',
  `biz_desc` varchar(100) NOT NULL COMMENT '业务描述',
  `current` bigint unsigned NOT NULL COMMENT '当前值',
  `step` int unsigned NOT NULL COMMENT '步长',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`biz_code`),
  UNIQUE KEY `biz_code_UNIQUE` (`biz_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `t_billno` WRITE;
INSERT INTO `t_billno` VALUES ('test','xx',30000,10000,'2024-08-01 14:16:46');
UNLOCK TABLES;
