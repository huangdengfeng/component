package com.seezoon.domain.dao.mapper;

import com.seezoon.domain.dao.po.UserProfilePO;

public interface UserProfileMapper {

    int deleteByPrimaryKey(Long uid);

    int insert(UserProfilePO row);

    UserProfilePO selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UserProfilePO row);

    int updateByPrimaryKey(UserProfilePO row);
}