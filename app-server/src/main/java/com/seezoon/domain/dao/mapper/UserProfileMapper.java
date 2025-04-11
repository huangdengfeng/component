package com.seezoon.domain.dao.mapper;

import com.seezoon.domain.dao.po.UserProfilePO;

public interface UserProfileMapper {

    UserProfilePO selectByMobile(String mobile);

    int deleteByPrimaryKey(Long uid);

    int insert(UserProfilePO row);

    UserProfilePO selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UserProfilePO row);

    int updateByPrimaryKey(UserProfilePO row);
}