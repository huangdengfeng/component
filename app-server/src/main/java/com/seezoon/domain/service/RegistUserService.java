package com.seezoon.domain.service;


import com.seezoon.domain.dao.mapper.OauthMapper;
import com.seezoon.domain.dao.mapper.UserMapper;
import com.seezoon.domain.dao.mapper.UserProfileMapper;
import com.seezoon.domain.dao.po.OauthPO;
import com.seezoon.domain.dao.po.UserPO;
import com.seezoon.domain.dao.po.UserProfilePO;
import com.seezoon.domain.valueobj.OauthType;
import com.seezoon.domain.valueobj.UserStatus;
import com.seezoon.infrastructure.constants.DbRecordStatus;
import com.seezoon.infrastructure.exception.Assertion;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
@Validated
public class RegistUserService {

    private final UserMapper userMapper;
    private final OauthMapper oauthMapper;
    private final UserProfileMapper userProfileMapper;

    public Long execute(@NotNull OauthType oauthType, @NotEmpty String oauthId, String unionId) {
        UserPO user = new UserPO();
        user.setSecretKey(genSecretKey());
        user.setStatus(UserStatus.VALID.code());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(user.getCreateTime());
        int affectedRows = userMapper.insert(user);
        Assertion.affectedOne(affectedRows);
        Assertion.notNull(user.getUid());

        this.saveOauth(user.getUid(), oauthType.type(), oauthId, unionId);
        this.saveProfile(user.getUid());

        return user.getUid();
    }

    private void saveOauth(Long uid, Byte oauthType, String oauthId, String unionId) {
        OauthPO po = new OauthPO();
        po.setUid(uid);
        po.setOauthType(oauthType);
        po.setOauthId(oauthId);
        po.setUnionId(unionId);
        po.setStatus(DbRecordStatus.VALID);
        po.setCreateTime(LocalDateTime.now());
        po.setUpdateTime(po.getCreateTime());
        int affectedRows = oauthMapper.insert(po);
        Assertion.affectedOne(affectedRows);
    }

    private void saveProfile(Long uid) {
        UserProfilePO po = new UserProfilePO();
        po.setUid(uid);
        po.setCreateTime(LocalDateTime.now());
        po.setUpdateTime(po.getCreateTime());
        int affectedRows = userProfileMapper.insert(po);
        Assertion.affectedOne(affectedRows);
    }

    private String genSecretKey() {
        return RandomStringUtils.randomAlphanumeric(32);
    }

}
