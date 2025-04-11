package com.seezoon.interfaces;

import com.seezoon.application.cms.dto.CmsBannerQry;
import com.seezoon.application.cms.dto.CmsContentPageQry;
import com.seezoon.application.cms.dto.clientobject.CmsBannerCO;
import com.seezoon.application.cms.dto.clientobject.CmsContentCO;
import com.seezoon.application.cms.dto.clientobject.CmsContentTypeCO;
import com.seezoon.application.cms.executor.CmsBannerQryExe;
import com.seezoon.application.cms.executor.CmsContentQryExe;
import com.seezoon.application.cms.executor.CmsContentTypeQryExe;
import com.seezoon.infrastructure.dto.Page;
import com.seezoon.infrastructure.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "公共信息", description = "如banner,协议等")
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    private final CmsBannerQryExe cmsBannerQryExe;
    private final CmsContentTypeQryExe cmsContentTypeQryExe;
    private final CmsContentQryExe cmsContentQryExe;

    @Operation(summary = "获取Banner列表")
    @PostMapping("/cms/banners")
    public Response<List<CmsBannerCO>> qryCmdBanners(@RequestBody CmsBannerQry qry) {
        return cmsBannerQryExe.execute(qry);
    }

    @Operation(summary = "获取内容类型列表")
    @GetMapping("/cms/content_type")
    public Response<List<CmsContentTypeCO>> qryCmsContentType() {
        return cmsContentTypeQryExe.execute();
    }

    @Operation(summary = "分页获取内容")
    @PostMapping("/cms/content")
    public Response<Page<CmsContentCO>> qryCmsContentPage(@RequestBody CmsContentPageQry qry) {
        return cmsContentQryExe.executePage(qry);
    }

    @Operation(summary = "获取单条内容")
    @PostMapping("/cms/content/{id}")
    public Response<CmsContentCO> qryCmsContent(@PathVariable Integer id) {
        return cmsContentQryExe.execute(id);
    }
}
