package com.seezoon.domain.service;

import static com.seezoon.infrastructure.exception.Assertion.affectedOne;

import com.seezoon.domain.dao.mapper.UserProfileMapper;
import com.seezoon.domain.dao.po.UserProfilePO;
import com.seezoon.infrastructure.error.ErrorCode;
import com.seezoon.infrastructure.exception.ExceptionFactory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Slf4j
@Service
@Validated
public class UserProfileService {

    private final UserProfileMapper userProfileMapper;

    /**
     * 更新用户手机号
     *
     * @param uid 用户ID
     * @param mobile 手机号
     * @return 是否更新成功
     */
    @Transactional
    public void updateMobile(@NotNull Long uid, @NotEmpty String mobile) {
        UserProfilePO po = userProfileMapper.selectByPrimaryKey(uid);
        if (po == null) {
            log.error("user profile not found, uid:{}", uid);
            throw ExceptionFactory.bizException(ErrorCode.USER_NOT_EXIST);
        }
        if (!Objects.equals(mobile, po.getMobile())) {
            return;
        }
        // 检查手机号是否已被其他用户使用，占用就解绑
        UserProfilePO existingProfile = userProfileMapper.selectByMobile(mobile);
        if (existingProfile != null && !Objects.equals(existingProfile.getUid(), uid)) {
            log.error("mobile already used by another user, mobile:{}, uid:{}", mobile, existingProfile.getUid());
            existingProfile.setMobile(null);
            existingProfile.setUpdateTime(LocalDateTime.now());
            affectedOne(userProfileMapper.updateByPrimaryKey(existingProfile));
        }

        po.setMobile(mobile);
        po.setUpdateTime(LocalDateTime.now());
        affectedOne(userProfileMapper.updateByPrimaryKey(po));
    }
} 