package com.seezoon.application.cms.dto;

import com.seezoon.infrastructure.dto.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsContentPageQry extends PageQuery {

    @Schema(title = "模糊搜索")
    private String fuzzyTitle;

    @Schema(title = "类型，调用类型接口获取")
    private Integer type;
}
