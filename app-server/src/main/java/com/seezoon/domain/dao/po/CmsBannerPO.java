package com.seezoon.domain.dao.po;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsBannerPO {

    private Integer id;

    private String name;

    private String position;

    private String url;

    private String target;

    private Integer sort;

    private Byte status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    
}