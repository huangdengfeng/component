package com.seezoon.domain.service.sys;

import com.seezoon.BaseSpringApplicationTest;
import com.seezoon.domain.service.sys.valueobj.AddUserVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author huangdengfeng
 * @date 2023/8/27 09:12
 */
class AddUserServiceTest extends BaseSpringApplicationTest {

    @Autowired
    private AddUserService addUserService;

    @Test
    void add() {
        AddUserVO vo = new AddUserVO("test_user_nam1e", "黄登峰");
        Integer uid = addUserService.add(vo, 1);
        Assertions.assertNotNull(uid);
    }
}