package com.seezoon.domain.dao.mapper;

import com.seezoon.domain.dao.po.CmsContentPO;
import com.seezoon.domain.dao.po.CmsContentPOCondition;
import java.util.List;

public interface CmsContentMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(CmsContentPO row);

    CmsContentPO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CmsContentPO row);

    int updateByPrimaryKey(CmsContentPO row);

    /**
     * 根据条件查询内容列表
     *
     * @param condition 查询条件
     * @return 内容列表
     */
    List<CmsContentPO> selectByCondition(CmsContentPOCondition condition);
} 