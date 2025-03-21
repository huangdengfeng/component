package com.seezoon.domain.dao.mapper;

import com.seezoon.domain.dao.po.OauthKeyPO;
import com.seezoon.domain.dao.po.OauthPO;
import org.apache.ibatis.annotations.Param;

public interface OauthMapper {

    int deleteByPrimaryKey(OauthKeyPO key);

    int insert(OauthPO row);

    OauthPO selectByPrimaryKey(OauthKeyPO key);

    OauthPO selectByOauth(@Param("oauthType") Byte oauthType, @Param("oauthId") String oauthId);

    int updateByPrimaryKeySelective(OauthPO row);

    int updateByPrimaryKey(OauthPO row);
}