package com.seezoon.interfaces;

import com.seezoon.application.user.dto.MobileLoginCmd;
import com.seezoon.application.user.dto.SendMobileLoginCaptchaCmd;
import com.seezoon.application.user.dto.UserPwdLoginCmd;
import com.seezoon.application.user.dto.WxMiniappLoginCmd;
import com.seezoon.application.user.dto.WxMiniappPhoneLoginCmd;
import com.seezoon.application.user.dto.WxPublicLoginCmd;
import com.seezoon.application.user.dto.clientobject.MobileLoginCO;
import com.seezoon.application.user.dto.clientobject.UserPwdLoginCO;
import com.seezoon.application.user.dto.clientobject.WxMiniappLoginCO;
import com.seezoon.application.user.dto.clientobject.WxMiniappPhoneLoginCO;
import com.seezoon.application.user.dto.clientobject.WxPublicLoginCO;
import com.seezoon.application.user.executor.MobileLoginCmdExe;
import com.seezoon.application.user.executor.SendMobileLoginCaptchaCmdExe;
import com.seezoon.application.user.executor.UserPwdLoginCmdExe;
import com.seezoon.application.user.executor.WxMiniappLoginCmdExe;
import com.seezoon.application.user.executor.WxMiniappPhoneLoginCmdExe;
import com.seezoon.application.user.executor.WxPublicLoginCmdExe;
import com.seezoon.infrastructure.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户登录", description = "用户登录处理")
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    private final WxMiniappLoginCmdExe wxMiniappLoginCmdExe;
    private final UserPwdLoginCmdExe userPwdLoginCmdExe;
    private final SendMobileLoginCaptchaCmdExe sendMobileLoginCaptchaCmdExe;
    private final MobileLoginCmdExe mobileLoginCmdExe;
    private final WxPublicLoginCmdExe wxPublicLoginCmdExe;
    private final WxMiniappPhoneLoginCmdExe wxMiniappPhoneLoginCmdExe;

    @Operation(summary = "小程序登录")
    @PostMapping("/wx_miniapp")
    public Response<WxMiniappLoginCO> wxMiniappLogin(@RequestBody WxMiniappLoginCmd cmd) {
        return wxMiniappLoginCmdExe.execute(cmd);
    }

    @Operation(summary = "账号密码登录")
    @PostMapping("/user_pwd")
    public Response<UserPwdLoginCO> userPwdLogin(@RequestBody UserPwdLoginCmd cmd) {
        return userPwdLoginCmdExe.execute(cmd);
    }

    @Operation(summary = "发送登录验证码")
    @PostMapping("/send_mobile_captcha")
    public Response sendMobileLoginCaptcha(@RequestBody SendMobileLoginCaptchaCmd cmd) {
        return sendMobileLoginCaptchaCmdExe.execute(cmd);
    }

    @Operation(summary = "手机号验证码登录")
    @PostMapping("/mobile")
    public Response<MobileLoginCO> mobileLogin(@RequestBody MobileLoginCmd cmd) {
        return mobileLoginCmdExe.execute(cmd);
    }

    @Operation(summary = "微信公众号登录")
    @PostMapping("/wx_public")
    public Response<WxPublicLoginCO> wxPublicLogin(@RequestBody WxPublicLoginCmd cmd) {
        return wxPublicLoginCmdExe.execute(cmd);
    }

    @Operation(summary = "微信小程序手机号快捷登录")
    @PostMapping("/wx_miniapp_phone")
    public Response<WxMiniappPhoneLoginCO> wxMiniappPhoneLogin(@RequestBody WxMiniappPhoneLoginCmd cmd) {
        return wxMiniappPhoneLoginCmdExe.execute(cmd);
    }
}
