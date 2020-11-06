package com.yufei.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yufei.educms.entity.CrmBanner;
import com.yufei.educms.mapper.CrmBannerMapper;
import com.yufei.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-02
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectAllBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 0,2");//拼接sql
        return baseMapper.selectList(wrapper);

    }
}
