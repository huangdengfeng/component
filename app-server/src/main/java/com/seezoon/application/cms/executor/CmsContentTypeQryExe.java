package com.seezoon.application.cms.executor;

import com.seezoon.application.cms.dto.clientobject.CmsContentTypeCO;
import com.seezoon.domain.dao.mapper.CmsContentTypeMapper;
import com.seezoon.domain.dao.po.CmsContentTypePO;
import com.seezoon.infrastructure.dto.Response;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 内容类型查询执行器
 * 负责查询指定状态的内容类型列表
 *
 * @author dfenghuang
 * @date 2024/3/21
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class CmsContentTypeQryExe {

    /**
     * 内容类型数据访问对象
     */
    private final CmsContentTypeMapper cmsContentTypeMapper;

    public Response<List<CmsContentTypeCO>> execute() {
        List<CmsContentTypePO> pos = cmsContentTypeMapper.selectValid();
        List<CmsContentTypeCO> cos = pos.stream().map(this::convert).collect(Collectors.toList());
        return Response.success(cos);
    }

    /**
     * 将内容类型持久化对象转换为客户端对象
     *
     * @param po 内容类型持久化对象
     * @return 内容类型客户端对象
     */
    private CmsContentTypeCO convert(CmsContentTypePO po) {
        CmsContentTypeCO co = new CmsContentTypeCO();
        co.setId(po.getId());
        co.setName(po.getName());
        return co;
    }
} 