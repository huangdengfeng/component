package com.seezoon.domain.valueobj;

public enum OauthType {

    WX_MINI_PROGRAM((byte) 1),
    WX_PUBLIC_PLATFORM((byte) 2);

    private byte type;

    OauthType(byte type) {
        this.type = type;
    }

    public byte type() {
        return type;
    }
}
