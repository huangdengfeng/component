# 输入示例

根据一个表结构，生成增删改查如下

```sql
CREATE TABLE `student_info`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT COMMENT '学生ID',
    `no`          varchar(64)  NOT NULL COMMENT '学号',
    `name`        varchar(255) NOT NULL COMMENT '姓名',
    `sex`         tinyint      NOT NULL COMMENT '性别：1、男；2、女',
    `introduce`   text        DEFAULT NULL COMMENT '介绍',
    `birthday`    date        DEFAULT NULL COMMENT '生日',
    `mobile`      varchar(45) DEFAULT NULL COMMENT '手机号',
    `status`      tinyint      NOT NULL COMMENT '状态：1、有效；2、无效',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `update_time` datetime     NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uidx_no` (`no`),
    KEY           `idx_name` (`name`),
    KEY           `idx_mobile` (`mobile`),
    KEY           `idx_status` (`status`),
    KEY           `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT '学生信息表';
```

# 生成示例

> 参考使用`lombok`和`swagger`，请阅读下例文件。

## Dao层

**PO类:** `src/main/java/com/seezoon/domain/dao/po/StudentInfoPO.java`
**Mapper接口:** `src/main/java/com/seezoon/domain/dao/mapper/StudentInfoMapper.java`
**XML映射:**`src/main/resources/mappings/StudentInfoMapper.xml`

## 领域层（只负责写相关）

**VO 类:** `src/main/java/com/seezoon/domain/service/student/vo/StudentVO.java`
**Service类:** `src/main/java/com/seezoon/domain/service/student/StudentService.java`

## 应用层

**DTO类 (写接口用cmd，查询用qry结尾，负责命令与职责分离)**:
`src/main/java/com/seezoon/application/student/dto/CreateStudentCmd.java`
`src/main/java/com/seezoon/application/student/dto/DeleteStudentCmd.java`
`src/main/java/com/seezoon/application/student/dto/StudentPageQry.java`
`src/main/java/com/seezoon/application/student/dto/UpdateStudentCmd.java`

**CO 类:**
`src/main/java/com/seezoon/application/student/dto/clientobject/StudentCO.java`

**执行器**
`src/main/java/com/seezoon/application/student/executor/CreateStudentCmdExe.java`
`src/main/java/com/seezoon/application/student/executor/DeleteStudentCmdExe.java`
`src/main/java/com/seezoon/application/student/executor/StudentPageQryExe.java`
`src/main/java/com/seezoon/application/student/executor/UpdateStudentCmdExe.java`

## 接口层

`src/main/java/com/seezoon/interfaces/StudentController.java`

请根据上述要求为下面表结构，生成增删改查，需要保持和代码代码风格一致。查询只需要使用表中有索引的字段。

```sql
CREATE TABLE `device_info`
(
    `id`                bigint      NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `device_no`         varchar(21) NOT NULL COMMENT '设备唯一ID(终端ID)',
    `sim_number`        varchar(20) NOT NULL COMMENT 'SIM卡号',
    `plate_number`      varchar(15) NOT NULL DEFAULT '' COMMENT '车牌号',
    `plate_color`       tinyint     NOT NULL COMMENT '车牌颜色(0:蓝,1:黄,2:白,3:黑)',
    `manufacturer_id`   varchar(11) NOT NULL COMMENT '制造商ID',
    `device_model`      varchar(30) NOT NULL COMMENT '设备型号',
    `province_id`       smallint    NOT NULL COMMENT '省域ID',
    `city_id`           smallint    NOT NULL COMMENT '市县域ID',
    `last_online_time`  datetime             DEFAULT NULL COMMENT '最后上线时间',
    `last_offline_time` datetime             DEFAULT NULL COMMENT '最后离线时间',
    `device_status`     tinyint     NOT NULL COMMENT '状态(0:离线,1:在线,2:休眠)',
    `auth_key`          varchar(64)          DEFAULT NULL COMMENT '鉴权密钥',
    `encryption_mode`   tinyint     NOT NULL COMMENT '加密方式(0:不加密,1:RSA)',
    `current_endpoint`  varchar(64) NOT NULL COMMENT '当前接入点',
    `create_time`       datetime    NOT NULL COMMENT '创建时间',
    `update_time`       datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_device_no` (`device_no`),
    KEY                 `idx_sim_number` (`sim_number`),
    KEY                 `idx_plate_number` (`plate_number`),
    KEY                 `idx_device_status` (`device_status`),
    KEY                 `idx_last_offline_time` (`last_offline_time`),
    KEY                 `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
COMMENT='设备信息表';
```