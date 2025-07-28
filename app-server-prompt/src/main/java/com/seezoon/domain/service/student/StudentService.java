package com.seezoon.domain.service.student;

import com.seezoon.application.student.dto.clientobject.StudentCO;
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


    /**
     * 转换为客户端对象
     */
    private StudentCO convertToCO(StudentInfoPO po) {
        StudentCO co = new StudentCO();
        co.setId(po.getId());
        co.setNo(po.getNo());
        co.setName(po.getName());
        co.setSex(po.getSex());
        co.setIntroduce(po.getIntroduce());
        co.setBirthday(po.getBirthday());
        co.setMobile(po.getMobile());
        co.setStatus(po.getStatus());
        co.setCreateTime(po.getCreateTime());
        co.setUpdateTime(po.getUpdateTime());
        return co;
    }
} 