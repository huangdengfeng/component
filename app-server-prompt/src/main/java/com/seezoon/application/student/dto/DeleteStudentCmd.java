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