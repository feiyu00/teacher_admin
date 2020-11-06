package com.yufei.educenter.controller;


import com.yufei.commonutils.JwtUtils;
import com.yufei.commonutils.R;
import com.yufei.educenter.entity.UcenterMember;
import com.yufei.educenter.entity.vo.RegisterVo;
import com.yufei.educenter.service.UcenterMemberService;
import org.apache.http.HttpRequest;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-03
 */
@RestController
@RequestMapping("/educenter/ucenter-member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;
    @PostMapping("login")
    public R login(@RequestBody UcenterMember ucenterMember){
        String token = memberService.login(ucenterMember);
        return R.ok().data("token",token);
    }
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }
    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }
}

