package com.yufei.educms.controller;

import com.yufei.commonutils.R;
import com.yufei.educms.entity.CrmBanner;
import com.yufei.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "前台Banner显示")
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class CrmFrontController {
    @Autowired
    private CrmBannerService bannerService;
    @ApiOperation("查询所有轮播图")
    @GetMapping("getAllBanner")
    public R getAllBanner(){
       List<CrmBanner> list =  bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }

}
