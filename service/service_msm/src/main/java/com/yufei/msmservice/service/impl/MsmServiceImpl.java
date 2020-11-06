package com.yufei.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.yufei.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {


    @Override
    public boolean send(Map<String, Object> param, String phone) {
        if (StringUtils.isEmpty(phone))return false;
        DefaultProfile profile = DefaultProfile.getProfile("default","LTAI4G7w2Fgu1dzY6Miuakq8","sGz1S1F5ZRlSlVpmSYcEu7GZbvtvkp");
        IAcsClient client = new DefaultAcsClient(profile);
        //不能改
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phone);//手机号
        request.putQueryParameter("SignName","ABC教育网址");//签名名称
        request.putQueryParameter("TemplateCode","SMS_205407874");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));//转换为json
        request.putQueryParameter("RegionId", "cn-beijing");
        try {
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            System.out.println(response.getData());
            return success;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }
}
