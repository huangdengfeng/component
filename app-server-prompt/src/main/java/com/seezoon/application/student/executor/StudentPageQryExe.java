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