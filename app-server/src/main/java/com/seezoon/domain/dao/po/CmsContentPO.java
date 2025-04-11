package com.seezoon.domain.dao.po;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsContentPO {

    private Integer id;

    private String title;

    private String description;

    private String content;

    private Integer type;

    private Integer views;

    private Byte status;

    private LocalDateTime publishTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Ext ext;

    public String getTypeName() {
        return null != ext ? ext.getTypeName() : null;
    }

    @Getter
    @Setter

    public static class Ext {

        private String typeName;
    }

} 