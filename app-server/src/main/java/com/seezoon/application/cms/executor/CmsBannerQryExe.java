package com.seezoon.application.cms.executor;

import com.seezoon.application.cms.dto.CmsBannerQry;
import com.seezoon.application.cms.dto.clientobject.CmsBannerCO;
import com.seezoon.domain.dao.mapper.CmsBannerMapper;
import com.seezoon.domain.dao.po.CmsBannerPO;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.properties.AppProperties;
import com.seezoon.infrastructure.utils.FilePathUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Banner查询执行器
 * 负责查询有效的Banner列表，并按sort字段升序排序
 *
 * @author dfenghuang
 * @date 2024/3/21
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class CmsBannerQryExe {

    /**
     * Banner数据访问对象
     */
    private final CmsBannerMapper cmsBannerMapper;
    private final AppProperties appProperties;

    /**
     * 执行Banner查询
     * 查询所有状态为有效的Banner，并按sort字段升序排序
     *
     * @return 包含Banner列表的响应对象
     */
    public Response<List<CmsBannerCO>> execute(@Valid @NotNull CmsBannerQry cmsBannerQry) {
        List<CmsBannerPO> pos = cmsBannerMapper.selectValidByPosition(cmsBannerQry.getPosition());
        List<CmsBannerCO> cos = pos.stream().map(this::convert).collect(Collectors.toList());
        return Response.success(cos);
    }

    /**
     * 将Banner持久化对象转换为客户端对象
     *
     * @param po Banner持久化对象
     * @return Banner客户端对象
     */
    private CmsBannerCO convert(CmsBannerPO po) {
        String fileUrlPrefix = appProperties.getFileUrlPrefix();
        CmsBannerCO co = new CmsBannerCO();
        co.setId(po.getId());
        co.setName(po.getName());
        co.setPosition(po.getPosition());
        co.setUrl(FilePathUtil.getFilePath(fileUrlPrefix, po.getUrl()));
        co.setTarget(po.getTarget());
        co.setSort(po.getSort());
        co.setStatus(po.getStatus());
        co.setCreateTime(po.getCreateTime());
        co.setUpdateTime(po.getUpdateTime());
        return co;
    }
} 