package com.seezoon.domain.service;

import com.seezoon.domain.dao.mapper.UserMapper;
import com.seezoon.domain.dao.po.UserPO;
import com.seezoon.infrastructure.security.PasswordEncoder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 用户密码验证服务
 *
 * @author huangdengfeng
 * @date 2023/9/10 23:30
 */
@RequiredArgsConstructor
@Slf4j
@Service
@Validated
@Transactional(readOnly = true)
public class UserPasswdVerifyService {

    private final UserMapper userMapper;

    public boolean verify(@NotEmpty String username, @NotEmpty String password) {
        UserPO po = userMapper.selectByUsername(username);
        String userPassword = po.getPassword();
        if (po == null || StringUtils.isEmpty(userPassword)) {
            return false;
        }
        if (PasswordEncoder.matches(password, userPassword)) {
            return true;
        }
        return false;
    }

    public boolean verify(@NotNull Long uid, @NotEmpty String password) {
        UserPO po = userMapper.selectByPrimaryKey(uid);
        String userPassword = po.getPassword();
        if (po == null || StringUtils.isEmpty(userPassword)) {
            return false;
        }
        if (PasswordEncoder.matches(password, userPassword)) {
            return true;
        }
        return false;
    }

}
