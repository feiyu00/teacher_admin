package com.yufei.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yufei.commonutils.JwtUtils;
import com.yufei.commonutils.MD5;
import com.yufei.educenter.entity.UcenterMember;
import com.yufei.educenter.entity.vo.RegisterVo;
import com.yufei.educenter.mapper.UcenterMemberMapper;
import com.yufei.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yufei.servicebase.exceptionhandler.GuiLiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-03
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    RedisTemplate<String,String> template;
    @Override
    public String login(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        //非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw  new GuiLiException(20001,"登录失败");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",member.getMobile());
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        if (ucenterMember == null){
            throw  new GuiLiException(20001,"用户名密码不存在！");
        }

        if (!MD5.encrypt(password).equals(ucenterMember.getPassword())){
            throw  new GuiLiException(20001,"账号密码不正确！");
        }
        //判断用户是否被禁用
        if (ucenterMember.getIsDisabled()){
            throw  new GuiLiException(20001,"账号已被禁用！");
        }
        return JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(password)){
            throw new GuiLiException(20001,"注册失败");
        }
        if (!code.equals(template.opsForValue().get(mobile))){
            throw new GuiLiException(20001,"验证码错误");
        }
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0){
            throw new GuiLiException(20001,"手机号已存在");
        }
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://edu-0105.oss-cn-beijing.aliyuncs.com/52855b3933ea50a1a6ed7d67515a3ac.png");
        baseMapper.insert(member);
    }
    @Override
    public UcenterMember getOpenIdNumber(String openid) {
        QueryWrapper<UcenterMember> wrapper= new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }
}
