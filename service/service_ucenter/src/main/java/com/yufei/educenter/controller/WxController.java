package com.yufei.educenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.yufei.commonutils.JwtUtils;
import com.yufei.commonutils.R;
import com.yufei.educenter.entity.UcenterMember;
import com.yufei.educenter.service.UcenterMemberService;
import com.yufei.educenter.utils.ConstantWxUtils;
import com.yufei.educenter.utils.HttpClientUtils;
import com.yufei.servicebase.exceptionhandler.GuiLiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "微信")
@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxController {
    @Autowired
    private UcenterMemberService memberService;
    @ApiOperation("获取扫码人信息，添加数据")
    @GetMapping("callback")
    public String callback(String code, String state) {

        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +

                "?appid=%s" +

                "&secret=%s" +

                "&code=%s" +

                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl,

                ConstantWxUtils.WX_OPEN_APP_ID,

                ConstantWxUtils.WX_OPEN_APP_SECRET,

                code);

        String result = null;

        try {

            result = HttpClientUtils.get(accessTokenUrl);
            /**
             *  result返回的数据
             *   用户凭证
             *  "access_token":"38_w-_ne4ar74xhIye1rO5PKiCva6emfY778Oefp0v1N-SZF6NXpstkjaKCmQ9z-mytiEetN1aRlw3yCczrPabMtcUCK7uNeUi4S9YcOC3l06E",
             *  "expires_in":7200, 过期时间
             *  "refresh_token":"38_iMi2IagkfRgN_6CJsG604LSE-BEiq3afXeGYMbKOBZtG2sWwk3ILe1wppGiYw1AsCtule5B0tOA6Nz6dReqVrLaQZNl8bJTqU2vmoYwANAE",
             *  "openid":"o3_SC5yXCUFGm-gjWLuOmEtDv02Y",用户微信id
             *  "scope":"snsapi_login",
             *  "unionid":"oWgGz1ImUCVBLtnD1sc3H28xVC-c"
             */
            System.out.println("accessToken=============" + result);
            Gson gson = new Gson();

            HashMap mapAccessToken = gson.fromJson(result, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid =(String) mapAccessToken.get("openid");
            //拿到access_token和openid请求微信提供的地址，获取用户信息
            UcenterMember member = memberService.getOpenIdNumber(openid);
            if (member == null){
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                String userInfo = HttpClientUtils.get(userInfoUrl);
                /**
                 * "openid":"o3_SC5yXCUFGm-gjWLuOmEtDv02Y",
                 * 昵称——————"nickname":"Jude",
                 * 性别——————"sex":1,
                 * 语言——————"language":"zh_CN",
                 * "city":"Nanchang",
                 * 籍贯——————"province":"Jiangxi",
                 * 国籍——————————"country":"CN",
                 * 微信头像————————"headimgurl":"https:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/ZxIsBNYbnOn6WEfnBJ6icLXWPwz6E0lgs23u7aCpQPkib7FYF9SS2bEVdDBy4icgPD7huqmtibcmzpsjRjpsL0wqibw\/132",
                 * "privilege":[],
                 * "unionid":"oWgGz1ImUCVBLtnD1sc3H28xVC-c"}
                 */
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String headImgUrl = (String) userInfoMap.get("headimgurl");//获取昵称和微信名称
                String nickname = (String) userInfoMap.get("nickname");
                System.out.println(userInfo);
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headImgUrl);
                memberService.save(member);
            }
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            return "redirect:http://localhost:3000?token="+jwtToken;
        } catch (Exception e) {
            throw new GuiLiException(20001, "获取access_token失败");
        }

    }

    @ApiOperation("生成微信二维码")
    @GetMapping("login")
    public String genQrConnect() {

        //微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +

                "&redirect_uri=%s" +

                "&response_type=code" +

                "&scope=snsapi_login" +

                "&state=%s" +

                "#wechat_redirect";

        // 回调地址

        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址

        try {

            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码

        } catch (UnsupportedEncodingException e) {

            throw new GuiLiException(20001, e.getMessage());

        }

        // 防止csrf攻击（跨站请求伪造攻击）

        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数

        String state = "atguigu";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名

        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置

        //键："wechar-open-state-" + httpServletRequest.getSession().getId()

        //值：satte

        //过期时间：30分钟


        //生成qrcodeUrl

        String qrcodeUrl = String.format(

                baseUrl,

                ConstantWxUtils.WX_OPEN_APP_ID,

                redirectUrl,

                state);


        return "redirect:" + qrcodeUrl;

    }
}
