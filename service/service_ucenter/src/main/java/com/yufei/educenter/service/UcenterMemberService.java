package com.yufei.educenter.service;

import com.yufei.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yufei.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-11-03
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdNumber(String openid);
}
