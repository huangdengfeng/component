package com.seezoon.application.cms.dto.clientobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seezoon.infrastructure.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsContentCO {

    private Integer id;
    private String title;
    @Schema(title = "摘要")
    private String description;
    @Schema(title = "富文本内容,分页查询时候不返回")
    private String content;
    private Integer type;
    private String typeName;
    private Integer views;

    @JsonFormat(pattern = Constants.DATETIME_PATTERN)
    private LocalDateTime publishTime;
} 