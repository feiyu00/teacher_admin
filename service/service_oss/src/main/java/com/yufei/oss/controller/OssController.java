package com.yufei.oss.controller;

import com.yufei.commonutils.R;
import com.yufei.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("eduoss/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    OssService ossService;
    //上传头像
    @PostMapping
    public R uploadOssFile(MultipartFile file){
        String url = ossService.uploadOssFileAvatar(file);
        return R.ok().data("url",url);
    }


}
