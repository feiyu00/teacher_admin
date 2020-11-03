package com.yufei.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.yufei.oss.service.OssService;
import com.yufei.oss.utils.ConstantProperties;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadOssFileAvatar(MultipartFile file) {
        //Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantProperties.END_POIND;
// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantProperties.ACCESS_KEY_ID;
        String accessKeySecret = ConstantProperties.ACCESS_KEY_SECRET;
        String bucketName = ConstantProperties.BUCKET_NAME;
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            //获取文件原始名称
            String fileName = file.getOriginalFilename();
            //防止文件名重复
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid+fileName;
            //获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd/");
            fileName = datePath+fileName;
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            //把上次之后文件的路径返回
            //需要把上传到阿里云的路径手动拼接出来
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return  url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
