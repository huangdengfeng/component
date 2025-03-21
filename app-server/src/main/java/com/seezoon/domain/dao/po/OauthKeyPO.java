package com.seezoon.domain.dao.po;

public class OauthKeyPO {

    private Long uid;

    private Byte oauthType;

    private String oauthId;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Byte getOauthType() {
        return oauthType;
    }

    public void setOauthType(Byte oauthType) {
        this.oauthType = oauthType;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }
}