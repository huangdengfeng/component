package com.seezoon.domain.dao.mapper;

import com.seezoon.domain.dao.po.CmsBannerPO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmsBannerMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(CmsBannerPO row);

    CmsBannerPO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CmsBannerPO row);

    int updateByPrimaryKey(CmsBannerPO row);

    /**
     * 根据位置查询有效的Banner列表
     *
     * @param position Banner位置
     * @return Banner列表
     */
    List<CmsBannerPO> selectValidByPosition(@Param("position") String position);
}