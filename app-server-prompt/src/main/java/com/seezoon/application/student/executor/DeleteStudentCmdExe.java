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