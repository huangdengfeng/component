package com.seezoon.application.cms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsBannerQry {

    @Schema(title = "banner位置，为空则全部查")
    private String position;
}
