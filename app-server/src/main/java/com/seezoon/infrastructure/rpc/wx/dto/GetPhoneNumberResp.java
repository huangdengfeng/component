package com.seezoon.infrastructure.rpc.wx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class GetPhoneNumberResp extends WxError {

    @JsonProperty("phone_info")
    private PhoneInfo phoneInfo;

    @Getter
    @Setter
    @ToString
    public static class PhoneInfo {
        @JsonProperty("phoneNumber")
        private String phoneNumber;

        @JsonProperty("purePhoneNumber")
        private String purePhoneNumber;

        @JsonProperty("countryCode")
        private String countryCode;

        private Watermark watermark;
    }

    @Getter
    @Setter
    @ToString
    public static class Watermark {
        private Long timestamp;
        private String appid;
    }
} 