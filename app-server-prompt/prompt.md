# 角色

你是一位Java高级开发工程师，精通Spring Boot和MyBatis框架，熟悉DDD领域分层设计。

# 任务

根据表结构，生成增删改查，需要保持和示例代码代码风格一致。查询只需要使用表中有索引的字段。

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

# 输入示例

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

## Dao层

**PO类:** `src/main/java/com/seezoon/domain/dao/po/StudentInfoPO.java`

```java
package com.seezoon.domain.dao.po;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentInfoPO {

    /**
     * 学生ID
     */
    private Integer id;
    /**
     * 学号
     */
    private String no;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别：1、男；2、女
     */
    private Byte sex;
    /**
     * 介绍
     */
    private String introduce;
    /**
     * 生日
     */
    private LocalDate birthday;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 状态：1、有效；2、无效；
     */
    private Byte status;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    @Getter
    @Setter
    public static class Condition {

        /**
         * 学生ID
         */
        private Integer id;
        /**
         * 学号
         */
        private String no;
        /**
         * 姓名
         */
        private String name;

        /**
         * 手机号
         */
        private String mobile;

        /**
         * 状态
         *
         * @see com.seezoon.infrastructure.constants.DbRecordStatus
         */
        private Byte status;
    }
}
```

**Mapper接口:** `src/main/java/com/seezoon/domain/dao/mapper/StudentInfoMapper.java`

```java
package com.seezoon.domain.dao.mapper;

import com.seezoon.domain.dao.po.StudentInfoPO;
import com.seezoon.domain.dao.po.StudentInfoPO.Condition;
import java.util.List;

public interface StudentInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(StudentInfoPO row);

    StudentInfoPO selectByPrimaryKey(Integer id);

    List<StudentInfoPO> selectByCondition(Condition condition);

    /**
     * 根据学号查询学生信息
     */
    StudentInfoPO selectByNo(String no);

    int updateByPrimaryKeySelective(StudentInfoPO row);

    int updateByPrimaryKey(StudentInfoPO row);
}
```

**XML映射:**`src/main/resources/mappings/StudentInfoMapper.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.seezoon.domain.dao.mapper.StudentInfoMapper">
    <resultMap id="BaseResultMap" type="com.seezoon.domain.dao.po.StudentInfoPO">
        <id column="id" jdbcType="INTEGER" property="id"/>
        <result column="no" jdbcType="VARCHAR" property="no"/>
        <result column="name" jdbcType="VARCHAR" property="name"/>
        <result column="sex" jdbcType="TINYINT" property="sex"/>
        <result column="introduce" jdbcType="LONGVARCHAR" property="introduce"/>
        <result column="birthday" jdbcType="DATE" property="birthday"/>
        <result column="mobile" jdbcType="VARCHAR" property="mobile"/>
        <result column="status" jdbcType="TINYINT" property="status"/>
        <result column="create_time" jdbcType="TIMESTAMP" property="createTime"/>
        <result column="update_time" jdbcType="TIMESTAMP" property="updateTime"/>
    </resultMap>

    <sql id="Base_Column_List">
        id, no, name, sex, introduce,birthday, mobile, status, create_time, update_time
    </sql>

    <select id="selectByPrimaryKey" parameterType="java.lang.Integer" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from student_info
        where id = #{id,jdbcType=INTEGER}
    </select>


    <select id="selectByNo" parameterType="java.lang.String" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from student_info
        where no = #{no,jdbcType=VARCHAR}
    </select>

    <select id="selectByCondition" resultMap="BaseResultMap"
            parameterType="com.seezoon.domain.dao.po.StudentInfoPO$Condition">
        select
        <include refid="Base_Column_List"/>
        from student_info
        <where>
            <if test="id != null">
                id = #{id,jdbcType=INTEGER}
            </if>
            <if test="no != null and no != ''">
                and no = #{no,jdbcType=VARCHAR}
            </if>
            <if test="name != null and name != ''">
                and name = #{name,jdbcType=VARCHAR}
            </if>
            <if test="mobile != null and mobile != ''">
                and mobile = #{mobile,jdbcType=VARCHAR}
            </if>
            <if test="status != null">
                and status = #{status,jdbcType=TINYINT}
            </if>
        </where>
    </select>

    <delete id="deleteByPrimaryKey" parameterType="java.lang.Integer">
        delete from student_info
        where id = #{id,jdbcType=INTEGER}
    </delete>

    <insert id="insert" parameterType="com.seezoon.domain.dao.po.StudentInfoPO"
            useGeneratedKeys="true" keyProperty="id" keyColumn="id">
        insert into student_info (id, no, name,
        sex, birthday, mobile,
        status, create_time, update_time,
        introduce)
        values (#{id,jdbcType=INTEGER}, #{no,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR},
        #{sex,jdbcType=TINYINT}, #{birthday,jdbcType=DATE}, #{mobile,jdbcType=VARCHAR},
        #{status,jdbcType=TINYINT}, #{createTime,jdbcType=TIMESTAMP},
        #{updateTime,jdbcType=TIMESTAMP},
        #{introduce,jdbcType=LONGVARCHAR})
    </insert>
    <update id="updateByPrimaryKeySelective"
            parameterType="com.seezoon.domain.dao.po.StudentInfoPO">
        update student_info
        <set>
            <if test="no != null">
                no = #{no,jdbcType=VARCHAR},
            </if>
            <if test="name != null">
                name = #{name,jdbcType=VARCHAR},
            </if>
            <if test="sex != null">
                sex = #{sex,jdbcType=TINYINT},
            </if>
            <if test="introduce != null">
                introduce = #{introduce,jdbcType=LONGVARCHAR},
            </if>
            <if test="birthday != null">
                birthday = #{birthday,jdbcType=DATE},
            </if>
            <if test="mobile != null">
                mobile = #{mobile,jdbcType=VARCHAR},
            </if>
            <if test="status != null">
                status = #{status,jdbcType=TINYINT},
            </if>
            <if test="createTime != null">
                create_time = #{createTime,jdbcType=TIMESTAMP},
            </if>
            <if test="updateTime != null">
                update_time = #{updateTime,jdbcType=TIMESTAMP},
            </if>
        </set>
        where id = #{id,jdbcType=INTEGER}
    </update>

    <update id="updateByPrimaryKey" parameterType="com.seezoon.domain.dao.po.StudentInfoPO">
        update student_info
        set no = #{no,jdbcType=VARCHAR},
        name = #{name,jdbcType=VARCHAR},
        sex = #{sex,jdbcType=TINYINT},
        introduce = #{introduce,jdbcType=LONGVARCHAR}
        birthday = #{birthday,jdbcType=DATE},
        mobile = #{mobile,jdbcType=VARCHAR},
        status = #{status,jdbcType=TINYINT},
        create_time = #{createTime,jdbcType=TIMESTAMP},
        update_time = #{updateTime,jdbcType=TIMESTAMP}
        where id = #{id,jdbcType=INTEGER}
    </update>
</mapper>
```

## 领域层（只负责写相关）

**VO 类:** `src/main/java/com/seezoon/domain/service/student/vo/StudentVO.java`、
`src/main/java/com/seezoon/domain/service/student/vo/StudentSexVO.java`

```java
package com.seezoon.domain.service.student.vo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentVO {

    /**
     * 学生ID
     */
    private Integer id;
    /**
     * 学号
     */
    @NotEmpty
    private String no;

    /**
     * 姓名
     */
    @NotEmpty
    private String name;

    /**
     * 性别
     *
     * @see StudentSexVO
     */
    @NotNull
    private Byte sex;

    /**
     * 介绍
     */
    private String introduce;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 状态
     *
     * @see com.seezoon.infrastructure.constants.DbRecordStatus
     */
    @NotNull
    private Byte status;
}
```

```java
package com.seezoon.domain.service.student.vo;

import com.seezoon.infrastructure.exception.Assertion;

public class StudentSexVO {

    /**
     * 男性
     */
    public static final Byte SEX_MALE = 1;
    /**
     * 女性
     */
    public static final Byte SEX_FEMALE = 2;

    public static void check(Byte code) {
        Assertion.isTrue(SEX_MALE == code || SEX_FEMALE == code,
                "student sex code is invalid: " + code);
    }
}
```

**Service类:** `src/main/java/com/seezoon/domain/service/student/StudentService.java`

```java
package com.seezoon.domain.service.student;

import com.seezoon.domain.dao.mapper.StudentInfoMapper;
import com.seezoon.domain.dao.po.StudentInfoPO;
import com.seezoon.domain.service.student.vo.StudentSexVO;
import com.seezoon.domain.service.student.vo.StudentVO;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.exception.Assertion;
import com.seezoon.infrastructure.exception.ExceptionFactory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 学生信息领域服务
 */
@RequiredArgsConstructor
@Slf4j
@Service
@Validated
public class StudentService {

    private final StudentInfoMapper studentInfoMapper;

    /**
     * 创建学生信息
     */
    @Transactional
    public Integer createStudent(@Valid @NotNull StudentVO vo) {
        StudentSexVO.check(vo.getSex());
        // 检查学号是否已存在
        StudentInfoPO existingStudent = studentInfoMapper.selectByNo(vo.getNo());
        if (existingStudent != null) {
            log.error("student no:{} exists,student id:{}", vo.getNo(), existingStudent.getId());
            throw ExceptionFactory.bizException(ErrorCode.PARAM_ILLEGAL);
        }

        StudentInfoPO po = new StudentInfoPO();
        po.setNo(vo.getNo());
        po.setName(vo.getName());
        po.setSex(vo.getSex());
        po.setIntroduce(vo.getIntroduce());
        po.setBirthday(vo.getBirthday());
        po.setMobile(vo.getMobile());
        po.setStatus(vo.getStatus());
        po.setCreateTime(LocalDateTime.now());
        po.setUpdateTime(LocalDateTime.now());

        int affectedRows = studentInfoMapper.insert(po);
        Assertion.affectedOne(affectedRows);
        return po.getId();
    }

    /**
     * 更新学生信息
     */
    @Transactional
    public void updateStudent(@Valid @NotNull StudentVO vo) {
        StudentSexVO.check(vo.getSex());
        Assertion.notNull(vo.getId(), "student id is null");
        StudentInfoPO po = studentInfoMapper.selectByPrimaryKey(vo.getId());
        if (po == null) {
            log.error("student not exists id:{}", vo.getId());
            throw ExceptionFactory.bizException(ErrorCode.RECORD_NOT_EXISTS);
        }
        // 检查学号是否被其他学生使用
        StudentInfoPO existingStudent = studentInfoMapper.selectByNo(vo.getNo());
        if (existingStudent != null && !existingStudent.getId().equals(vo.getId())) {
            log.error("student no already used no:{}", vo.getNo());
            throw ExceptionFactory.bizException(ErrorCode.PARAM_ILLEGAL);
        }

        po.setNo(vo.getNo());
        po.setName(vo.getName());
        po.setSex(vo.getSex());
        po.setIntroduce(vo.getIntroduce());
        po.setBirthday(vo.getBirthday());
        po.setMobile(vo.getMobile());
        po.setUpdateTime(LocalDateTime.now());

        int affectedRows = studentInfoMapper.updateByPrimaryKey(po);
        Assertion.affectedOne(affectedRows);
    }

    /**
     * 删除学生信息
     */
    @Transactional
    public void deleteStudent(@NotNull Integer id) {
        StudentInfoPO po = studentInfoMapper.selectByPrimaryKey(id);
        if (po == null) {
            log.error("student not exists，id:{}", id);
            throw ExceptionFactory.bizException(ErrorCode.RECORD_NOT_EXISTS);
        }

        int affectedRows = studentInfoMapper.deleteByPrimaryKey(id);
        Assertion.affectedOne(affectedRows);
    }
}
```

## 应用层

**DTO类 (写接口用cmd，查询用qry结尾，负责命令与职责分离)**:
`src/main/java/com/seezoon/application/student/dto/CreateStudentCmd.java`

```java
package com.seezoon.application.student.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seezoon.infrastructure.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * 创建学生信息
 */
@Getter
@Setter
public class CreateStudentCmd {

    @Schema(description = "学号")
    @NotEmpty
    @Size(max = 64)
    private String no;

    @Schema(description = "姓名")
    @NotEmpty
    @Size(max = 255)
    private String name;

    @Schema(description = "性别：1、男；2、女")
    @NotNull
    private Byte sex;

    @Schema(description = "介绍")
    private String introduce;

    @Schema(description = "生日")
    @JsonFormat(pattern = Constants.DATE_PATTERN)
    private LocalDate birthday;

    @Schema(description = "手机号")
    @Size(max = 45)
    private String mobile;
} 
```

`src/main/java/com/seezoon/application/student/dto/DeleteStudentCmd.java`

```java
package com.seezoon.application.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 删除学生信息
 */
@Getter
@Setter
public class DeleteStudentCmd {

    @Schema(description = "学生ID")
    @NotNull
    private Integer id;
} 
```

`src/main/java/com/seezoon/application/student/dto/StudentPageQry.java`

```java
package com.seezoon.application.student.dto;

import com.seezoon.infrastructure.dto.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 获取学生信息
 */
@Getter
@Setter
public class StudentPageQry extends PageQuery {

    @Schema(description = "学生ID")
    private Integer id;

    @Schema(description = "学号")
    private String no;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "状态：1、有效；2、无效")
    private Byte status;
}
```

`src/main/java/com/seezoon/application/student/dto/UpdateStudentCmd.java`

```java
package com.seezoon.application.student.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seezoon.infrastructure.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * 更新学生信息
 */
@Getter
@Setter
public class UpdateStudentCmd {

    @Schema(description = "学生ID")
    @NotNull
    private Integer id;

    @Schema(description = "学号")
    @NotEmpty
    @Size(max = 64)
    private String no;

    @Schema(description = "姓名")
    @NotEmpty
    @Size(max = 255)
    private String name;

    @Schema(description = "性别：1、男；2、女")
    @NotNull
    private Byte sex;

    @Schema(description = "介绍")
    private String introduce;

    @Schema(description = "生日")
    @JsonFormat(pattern = Constants.DATE_PATTERN)
    private LocalDate birthday;

    @Schema(description = "手机号")
    @Size(max = 45)
    private String mobile;

    @Schema(description = "状态：1、有效；2、停用")
    @NotNull
    private Byte status;
} 
```

**CO 类:**
`src/main/java/com/seezoon/application/student/dto/clientobject/StudentCO.java`

```java
package com.seezoon.application.student.dto.clientobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seezoon.infrastructure.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 学生信息客户端对象
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentCO {

    @Schema(title = "学生ID")
    private Integer id;

    @Schema(title = "学号")
    private String no;

    @Schema(title = "姓名")
    private String name;

    @Schema(title = "性别：1、男；2、女")
    private Byte sex;

    @Schema(title = "介绍")
    private String introduce;

    @Schema(title = "生日")
    @JsonFormat(pattern = Constants.DATE_PATTERN)
    private LocalDate birthday;

    @Schema(title = "手机号")
    private String mobile;

    @Schema(title = "状态：1、有效；2、无效")
    private Byte status;

    @Schema(title = "创建时间")
    @JsonFormat(pattern = Constants.DATETIME_PATTERN)
    private LocalDateTime createTime;

    @JsonFormat(pattern = Constants.DATETIME_PATTERN)
    @Schema(title = "更新时间")
    private LocalDateTime updateTime;
} 
```

**执行器**
`src/main/java/com/seezoon/application/student/executor/CreateStudentCmdExe.java`

```java
package com.seezoon.application.student.executor;

import com.seezoon.application.student.dto.CreateStudentCmd;
import com.seezoon.domain.service.student.StudentService;
import com.seezoon.domain.service.student.vo.StudentVO;
import com.seezoon.infrastructure.constants.DbRecordStatus;
import com.seezoon.infrastructure.dto.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 创建学生信息
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class CreateStudentCmdExe {

    private final StudentService studentService;

    public Response execute(@Valid @NotNull CreateStudentCmd cmd) {
        StudentVO vo = new StudentVO();
        vo.setNo(cmd.getNo());
        vo.setName(cmd.getName());
        vo.setSex(cmd.getSex());
        vo.setIntroduce(cmd.getIntroduce());
        vo.setBirthday(cmd.getBirthday());
        vo.setMobile(cmd.getMobile());
        vo.setStatus(DbRecordStatus.VALID);

        Integer id = studentService.createStudent(vo);
        log.info("create student success id:{}", id);
        return Response.success();
    }
} 
```

`src/main/java/com/seezoon/application/student/executor/DeleteStudentCmdExe.java`

```java
package com.seezoon.application.student.executor;

import com.seezoon.application.student.dto.DeleteStudentCmd;
import com.seezoon.domain.service.student.StudentService;
import com.seezoon.infrastructure.dto.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 删除学生信息
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class DeleteStudentCmdExe {

    private final StudentService studentService;

    public Response<Void> execute(@Valid @NotNull DeleteStudentCmd cmd) {
        studentService.deleteStudent(cmd.getId());
        return Response.success();
    }
} 
```

`src/main/java/com/seezoon/application/student/executor/StudentPageQryExe.java`

```java
package com.seezoon.application.student.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.seezoon.application.student.dto.StudentPageQry;
import com.seezoon.application.student.dto.clientobject.StudentCO;
import com.seezoon.domain.dao.mapper.StudentInfoMapper;
import com.seezoon.domain.dao.po.StudentInfoPO;
import com.seezoon.infrastructure.dto.Page;
import com.seezoon.infrastructure.dto.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 获取学生信息
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class StudentPageQryExe {

    private final StudentInfoMapper studentInfoMapper;

    public Response<Page<StudentCO>> execute(@Valid @NotNull StudentPageQry qry) {
        StudentInfoPO.Condition condition = new StudentInfoPO.Condition();
        condition.setId(qry.getId());
        condition.setName(qry.getName());
        condition.setNo(qry.getNo());
        condition.setMobile(qry.getMobile());
        condition.setStatus(qry.getStatus());
        PageHelper.startPage(qry.getPage(), qry.getPageSize());
        PageSerializable<StudentInfoPO> page = new PageSerializable<>(
                studentInfoMapper.selectByCondition(condition));
        List<StudentCO> data = new ArrayList<>();
        page.getList().forEach(item -> {
            StudentCO co = new StudentCO();
            data.add(co);
        });
        return Response.success(new Page<>(page.getTotal(), data));
    }
} 
```

`src/main/java/com/seezoon/application/student/executor/UpdateStudentCmdExe.java`

```java
package com.seezoon.application.student.executor;

import com.seezoon.application.student.dto.UpdateStudentCmd;
import com.seezoon.domain.service.student.StudentService;
import com.seezoon.domain.service.student.vo.StudentVO;
import com.seezoon.infrastructure.dto.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 更新学生信息
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class UpdateStudentCmdExe {

    private final StudentService studentService;

    public Response execute(@Valid @NotNull UpdateStudentCmd cmd) {
        StudentVO vo = new StudentVO();
        vo.setId(cmd.getId());
        vo.setNo(cmd.getNo());
        vo.setName(cmd.getName());
        vo.setSex(cmd.getSex());
        vo.setIntroduce(cmd.getIntroduce());
        vo.setBirthday(cmd.getBirthday());
        vo.setMobile(cmd.getMobile());
        vo.setStatus(cmd.getStatus());
        studentService.updateStudent(vo);
        return Response.success();
    }
} 
```

## 接口层

`src/main/java/com/seezoon/interfaces/StudentController.java`

```java
package com.seezoon.interfaces;

import com.seezoon.application.student.dto.CreateStudentCmd;
import com.seezoon.application.student.dto.DeleteStudentCmd;
import com.seezoon.application.student.dto.StudentPageQry;
import com.seezoon.application.student.dto.UpdateStudentCmd;
import com.seezoon.application.student.dto.clientobject.StudentCO;
import com.seezoon.application.student.executor.CreateStudentCmdExe;
import com.seezoon.application.student.executor.DeleteStudentCmdExe;
import com.seezoon.application.student.executor.StudentPageQryExe;
import com.seezoon.application.student.executor.UpdateStudentCmdExe;
import com.seezoon.infrastructure.dto.Page;
import com.seezoon.infrastructure.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生信息管理
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/student")
@Tag(name = "学生信息管理")
public class StudentController {

    private final CreateStudentCmdExe createStudentCmdExe;
    private final UpdateStudentCmdExe updateStudentCmdExe;
    private final DeleteStudentCmdExe deleteStudentCmdExe;
    private final StudentPageQryExe studentPageQryExe;

    @PostMapping("/create")
    @Operation(summary = "创建学生信息")
    public Response<StudentCO> createStudent(@RequestBody CreateStudentCmd cmd) {
        return createStudentCmdExe.execute(cmd);
    }

    @PostMapping("/update")
    @Operation(summary = "更新学生信息")
    public Response<StudentCO> updateStudent(@Valid @RequestBody UpdateStudentCmd cmd) {
        return updateStudentCmdExe.execute(cmd);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除学生信息")
    public Response<Void> deleteStudent(@RequestBody DeleteStudentCmd cmd) {
        return deleteStudentCmdExe.execute(cmd);
    }

    @PostMapping("/page")
    @Operation(summary = "获取学生信息")
    public Response<Page<StudentCO>> studentPage(@RequestBody StudentPageQry qry) {
        return studentPageQryExe.execute(qry);
    }
} 
```


