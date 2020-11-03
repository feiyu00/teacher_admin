package com.yufei.educms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yufei.commonutils.R;
import com.yufei.educms.entity.CrmBanner;
import com.yufei.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-02
 */
@RestController
@RequestMapping("/educms/crm-banner")
@CrossOrigin
public class CrmBannerController {
    @Autowired
    private CrmBannerService bannerService;
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit){
        Page<CrmBanner> pageBanner = new Page<>(page,limit);
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        bannerService.page(pageBanner, null);
        List<CrmBanner> records = pageBanner.getRecords();
        long total = pageBanner.getTotal();
        return  R.ok().data("items",records).data("total",total);
    }
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        boolean save = bannerService.save(crmBanner);
        if (save){
            return R.ok();
        }else {
            return R.error().message("添加失败").code(10002);
        }
    }
    @PutMapping("update")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        boolean result = bannerService.updateById(crmBanner);
        if (result){
            return R.ok();
        }else {
            return R.error().message("更新失败").code(10002);
        }
    }
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable long id){
        boolean result = bannerService.removeById(id);
        if (result){
            return R.ok();
        }else {
            return R.error().code(10002).message("删除失败");
        }
    }
    @GetMapping("get/{id}")
    public R get(@PathVariable long id){
        CrmBanner banner = bannerService.getById(id);
        return  R.ok().data("item",banner);
    }
}

