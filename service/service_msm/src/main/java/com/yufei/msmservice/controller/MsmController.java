package com.yufei.msmservice.controller;

import com.yufei.commonutils.R;
import com.yufei.msmservice.service.MsmService;
import com.yufei.msmservice.utlis.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService  msmService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    //发送短信
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone){
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            return R.ok();
        }
        code  = RandomUtils.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        boolean isSend = msmService.send(param,phone);
        if (isSend){
            //发送成功的验证码放到redis里面,5分钟有效
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }else {
            return R.error().message("短信发送失败");
        }
    }
}
