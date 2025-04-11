package com.seezoon.interfaces;

import com.seezoon.application.user.dto.BindWxPhoneNumberCmd;
import com.seezoon.application.user.dto.clientobject.UserProfileCO;
import com.seezoon.application.user.executor.BindWxPhoneNumberCmdExe;
import com.seezoon.application.user.executor.UserProfileQryExe;
import com.seezoon.infrastructure.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户信息", description = "用户信息接口相关")
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserProfileQryExe userProfileQryExe;
    private final BindWxPhoneNumberCmdExe bindWxPhoneNumberCmdExe;

    @Operation(summary = "用户个人信息")
    @GetMapping("/info")
    public Response<UserProfileCO> info() {
        return userProfileQryExe.execute();
    }

    @Operation(summary = "绑定微信小程序手机号")
    @PostMapping("/bind_wx_phone")
    public Response bindWxPhoneNumber(@RequestBody BindWxPhoneNumberCmd cmd) {
        return bindWxPhoneNumberCmdExe.execute(cmd);
    }
}
