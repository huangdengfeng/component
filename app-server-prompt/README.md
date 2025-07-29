# API doc

http://127.0.0.1:8080/swagger-ui/index.html#/

# 代码生成提示示例

请根据StudentController，并参考当前代码风格。为下列表结构生增删改查；查询只需要支持表结构中有索引的字段即可。

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
