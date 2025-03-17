package com.seezoon.interfaces.sys;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.seezoon.BaseSpringApplicationTest;
import com.seezoon.application.sys.authentication.dto.UserPasswdLoginCmd;
import com.seezoon.application.sys.authentication.executor.UserPasswdLoginCmdExe;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class SysLoginControllerTest extends BaseSpringApplicationTest {

    @MockitoBean
    private UserPasswdLoginCmdExe userPasswdLoginCmdExe;

    @BeforeEach
    public void before() {
        when(userPasswdLoginCmdExe.execute(any())).thenReturn(Response.success());
    }

    @Test
    public void userPasswdLogin_WithValidCredentials_ShouldReturnSuccess() throws Exception {
        UserPasswdLoginCmd cmd = new UserPasswdLoginCmd();
        cmd.setUsername("admin");
        cmd.setPassword("123456");
        mockMvc.perform(MockMvcRequestBuilders.post("/sys/login/user_passwd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(cmd)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

}