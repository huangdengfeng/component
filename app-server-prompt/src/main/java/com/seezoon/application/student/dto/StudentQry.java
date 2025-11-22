package com.seezoon.application.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 查询学生信息
 */
@Getter
@Setter
public class StudentQry {

    @Schema(description = "学生ID")
    @NotNull
    private Integer id;
}

