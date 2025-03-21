package com.seezoon.interfaces;

import com.seezoon.application.dto.clientobject.UserProfileCO;
import com.seezoon.application.executor.UserProfileQryExe;
import com.seezoon.infrastructure.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户信息", description = "用户信息接口相关")
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserProfileQryExe userProfileQryExe;

    @Operation(summary = "用户个人信息")
    @GetMapping("/info")
    public Response<UserProfileCO> info() {
        return userProfileQryExe.execute();
    }
}
