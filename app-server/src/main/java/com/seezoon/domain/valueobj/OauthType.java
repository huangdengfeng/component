package com.seezoon.domain.valueobj;

public enum OauthType {

    WX((byte) 1);

    private byte type;

    OauthType(byte type) {
        this.type = type;
    }

    public byte type() {
        return type;
    }
}
