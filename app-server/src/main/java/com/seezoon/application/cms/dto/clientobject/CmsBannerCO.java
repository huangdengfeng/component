package com.seezoon.application.cms.dto.clientobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seezoon.infrastructure.constants.Constants;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsBannerCO {

    private Integer id;
    private String name;
    private String position;
    private String url;
    private String target;
    private Integer sort;
    private Byte status;

    @JsonFormat(pattern = Constants.DATETIME_PATTERN)
    private LocalDateTime createTime;

    @JsonFormat(pattern = Constants.DATETIME_PATTERN)
    private LocalDateTime updateTime;

} 