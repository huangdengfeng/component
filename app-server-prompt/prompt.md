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

# 生成要求
参考使用`lombok`和`swagger`

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