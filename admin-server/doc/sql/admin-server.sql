CREATE DATABASE  IF NOT EXISTS `admin-server` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `admin-server`;

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(45) NOT NULL COMMENT '权限编码',
  `name` varchar(45) NOT NULL COMMENT '名称',
  `parent_id` int DEFAULT NULL COMMENT '父ID',
  `parent_ids` varchar(200) DEFAULT NULL COMMENT '所有父节点',
  `status` tinyint NOT NULL COMMENT '状态',
  `create_user` int NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` int NOT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `sys_permission` VALUES (1,'sys:user','用户管理',NULL,NULL,1,1,'2023-10-20 23:07:16',1,'2023-10-20 23:07:16'),(2,'sys:user:qry','用户查询',1,'1',1,1,'2023-10-20 23:07:16',1,'2023-10-20 23:07:16'),(3,'sys:user:add','添加',1,'1',1,1,'2023-10-20 23:07:16',1,'2023-10-20 23:07:16'),(4,'sys:user:modify','修改用户',1,'1',1,1,'2023-10-20 23:07:16',1,'2023-10-20 23:07:16'),(5,'sys:user:modify_pwd','修改用户密码',1,'1',1,1,'2023-10-20 23:07:16',1,'2023-10-20 23:07:16'),(6,'sys:role','角色管理',NULL,NULL,1,1,'2023-10-20 23:07:16',1,'2023-10-20 23:07:16'),(7,'sys:role:add','添加',6,'6',1,1,'2023-10-20 23:07:16',1,'2023-10-20 23:07:16'),(8,'sys:role:modify','修改',6,'6',1,1,'2023-10-20 23:07:16',1,'2023-10-20 23:07:16'),(9,'sys:role:delete','删除',6,'6',1,1,'2023-10-20 23:07:16',1,'2023-10-20 23:07:16'),(10,'sys:permission','权限管理',NULL,NULL,1,1,'2023-10-20 23:07:16',1,'2023-10-20 23:07:16'),(11,'sys:permission:add','添加',10,'10',1,1,'2023-10-20 23:07:16',1,'2023-10-20 23:07:16'),(12,'sys:permission:modify','修改',10,'10',1,1,'2023-10-20 23:07:16',1,'2023-10-20 23:07:16'),(13,'sys:permission:delete','删除',10,'10',1,1,'2023-10-20 23:07:16',1,'2023-10-20 23:07:16');

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'role id',
  `code` varchar(45) NOT NULL COMMENT '角色编码',
  `name` varchar(45) NOT NULL COMMENT '角色名称',
  `status` tinyint NOT NULL COMMENT '状态',
  `create_user` int NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user` int NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `sys_role` VALUES (1,'code1','name',1,1,'2023-11-10 15:34:03',1,'2023-11-10 15:34:03');


DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `role_id` int NOT NULL COMMENT '角色ID',
  `permission_id` int NOT NULL COMMENT '权限ID',
  `create_user` int NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `uid` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(50) NOT NULL COMMENT 'username ',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `secret_key` varchar(32) NOT NULL COMMENT '安全密钥',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `photo` varchar(100) DEFAULT NULL COMMENT '照片',
  `status` tinyint NOT NULL COMMENT '状态：1.正常;2.停用;3.锁定',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` int NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_user` int NOT NULL COMMENT '更新用户',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `sys_user` VALUES (1,'admin','$2a$10$hTbIfacFP2Vl0EX.qmQBB.WI/zESwGc2WiMOnykfBzOoiiKIGSGCa','NYivApKMepQQLYul5xGegsUQA440ox4h','管理员',NULL,NULL,NULL,1,'2023-08-27 09:16:23',1,'2023-10-20 22:55:18',1,NULL),(18,'test-username','','111','test-name','','','',1,'2023-10-26 22:17:26',1,'2023-10-26 22:17:26',1,''),(20,'test1','$2a$10$2KJcubLvi3o0zE8V.uHHYusFW0rQCa9PcZKP3EpdozneskK/56Laa','vUHCZY4D1m6XX5mj76xicoLZ92QF1dkX','test1','','','',1,'2023-11-17 00:07:08',1,'2023-11-17 00:07:08',1,''),(21,'5435345','$2a$10$B0k3lNFvmYzCINNiXamWFuEPKvNbFrVrIjI9B7oZUd2wsVCzIHMLC','YNHQ2V8rWHWrQsXudIHWHcOL3zbbnRyo','53454','12121额外恶趣味额度','','',1,'2023-11-17 09:38:56',1,'2023-11-17 09:38:56',1,'');


DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `uid` int NOT NULL,
  `role_id` int NOT NULL,
  `create_user` int NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`uid`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `sys_user_role` VALUES (1,1,1,'2023-11-10 15:34:53');
