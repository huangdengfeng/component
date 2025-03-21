package com.seezoon.interfaces;

import com.seezoon.application.dto.UserPwdLoginCmd;
import com.seezoon.application.dto.WxMiniappLoginCmd;
import com.seezoon.application.dto.clientobject.UserPwdLoginCO;
import com.seezoon.application.dto.clientobject.WxMiniappLoginCO;
import com.seezoon.application.executor.UserPwdLoginCmdExe;
import com.seezoon.application.executor.WxMiniappLoginCmdExe;
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
}
