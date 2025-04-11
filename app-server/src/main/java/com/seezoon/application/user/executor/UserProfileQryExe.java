package com.seezoon.application.user.executor;

import com.seezoon.application.user.dto.clientobject.UserProfileCO;
import com.seezoon.domain.dao.mapper.UserProfileMapper;
import com.seezoon.domain.dao.po.UserProfilePO;
import com.seezoon.infrastructure.configuration.context.SecurityContextHolder;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.exception.Assertion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class UserProfileQryExe {

    private final UserProfileMapper userProfileMapper;

    public Response<UserProfileCO> execute() {
        Long uid = SecurityContextHolder.getUid();
        Assertion.notNull(uid);
        UserProfilePO po = userProfileMapper.selectByPrimaryKey(uid);
        UserProfileCO co = new UserProfileCO();
        co.setUid(po.getUid());
        co.setNickName(po.getNickName());
        co.setName(po.getName());
        co.setMobile(po.getMobile());
        co.setAvatar(po.getAvatar());
        co.setEmail(po.getEmail());
        co.setBirthday(po.getBirthday());
        co.setAddress(po.getAddress());
        co.setCreateTime(po.getCreateTime());
        return Response.success(co);
    }
}
