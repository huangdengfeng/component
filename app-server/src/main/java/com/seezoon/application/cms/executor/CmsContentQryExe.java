package com.seezoon.application.cms.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.seezoon.application.cms.dto.CmsContentPageQry;
import com.seezoon.application.cms.dto.clientobject.CmsContentCO;
import com.seezoon.domain.dao.mapper.CmsContentMapper;
import com.seezoon.domain.dao.po.CmsContentPO;
import com.seezoon.domain.dao.po.CmsContentPOCondition;
import com.seezoon.infrastructure.constants.DbRecordStatus;
import com.seezoon.infrastructure.dto.Page;
import com.seezoon.infrastructure.dto.Response;
import com.seezoon.infrastructure.error.ErrorCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Slf4j
@Component
@Validated
public class CmsContentQryExe {

    private final CmsContentMapper cmsContentMapper;

    public Response<Page<CmsContentCO>> executePage(@Valid @NotNull CmsContentPageQry qry) {
        CmsContentPOCondition condition = new CmsContentPOCondition();
        condition.setType(qry.getType());
        condition.setFuzzyTitle(qry.getFuzzyTitle());
        condition.setStatus(DbRecordStatus.VALID);
        PageHelper.startPage(qry.getPage(), qry.getPageSize());
        PageSerializable<CmsContentPO> pageInfo = new PageSerializable<>(cmsContentMapper.selectByCondition(condition));
        List<CmsContentCO> cos = pageInfo.getList().stream().map(this::convert).collect(Collectors.toList());
        return Response.success(new Page<>(pageInfo.getTotal(), cos));
    }

    public Response<CmsContentCO> execute(@NotNull Integer id) {
        CmsContentPO cmsContentPO = cmsContentMapper.selectByPrimaryKey(id);
        if (cmsContentPO == null || !DbRecordStatus.valid(cmsContentPO.getStatus())) {
            return Response.error(ErrorCode.RECORD_NOT_EXISTS.code(), ErrorCode.RECORD_NOT_EXISTS.msg());
        }
        return Response.success(this.convert(cmsContentPO));
    }
    

    private CmsContentCO convert(CmsContentPO po) {
        if (po == null) {
            return null;
        }
        CmsContentCO co = new CmsContentCO();
        co.setId(po.getId());
        co.setTitle(po.getTitle());
        co.setDescription(po.getDescription());
        co.setContent(po.getContent());
        co.setType(po.getType());
        co.setTypeName(po.getTypeName());
        co.setViews(po.getViews());
        co.setPublishTime(po.getPublishTime());
        return co;
    }


}
