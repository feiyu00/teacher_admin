package com.yufei.eduservice.controller;

import com.yufei.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "登录请求")
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }
    @ApiOperation(value = "添加课程")
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://dss1.bdstatic.com/6OF1bjeh1BF3odCf/it/u=3862291331,881805860&fm=85&app=92&f=JPEG");
    }
}
