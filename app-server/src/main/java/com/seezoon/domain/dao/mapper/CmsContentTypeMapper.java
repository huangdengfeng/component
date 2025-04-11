package com.seezoon.domain.dao.mapper;

import com.seezoon.domain.dao.po.CmsContentTypePO;
import java.util.List;

public interface CmsContentTypeMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(CmsContentTypePO row);

    CmsContentTypePO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CmsContentTypePO row);

    int updateByPrimaryKey(CmsContentTypePO row);

    List<CmsContentTypePO> selectValid();
} 