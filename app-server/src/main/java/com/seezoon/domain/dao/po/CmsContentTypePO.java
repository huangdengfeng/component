package com.seezoon.domain.dao.po;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsContentTypePO {

    private Integer id;

    private String name;

    private Byte status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
    
} 