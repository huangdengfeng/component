package com.seezoon.interfaces.sys;

import com.seezoon.BaseSpringApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class SysLoginControllerTest extends BaseSpringApplicationTest {

    @Test
    void userPasswdLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sys/login/user_passwd"));
    }
}