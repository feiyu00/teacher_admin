package com.yufei.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.yufei.commonutils.R;
import com.yufei.servicebase.exceptionhandler.GuiLiException;
import com.yufei.vod.service.VodService;
import com.yufei.vod.utils.ConstantProperties;
import com.yufei.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadAliVideo(MultipartFile file) throws IOException {
        String fileName =file.getOriginalFilename();
        String accessKeyId = ConstantProperties.ACCESS_KEY_ID;
        String accessKeySecret = ConstantProperties.ACCESS_KEY_SECRET;
        InputStream inputStream = file.getInputStream();
        //01.mp4 截取为 01
        String title =fileName.substring(0,fileName.lastIndexOf("."));
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId,accessKeySecret , title ,fileName, inputStream);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        String videoId = null;
        if (response.isSuccess()) {
            videoId = response.getVideoId();
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            videoId = response.getVideoId();
        }
        return videoId;
    }

    @Override
    public R removeAlyVideo(String id) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantProperties.ACCESS_KEY_ID, ConstantProperties.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuiLiException(20001,"删除视频失败");
        }

    }

    @Override
    public void removeMoreAlyVideo(List videoIdList) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantProperties.ACCESS_KEY_ID, ConstantProperties.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            //分割符
            String join = StringUtils.join(videoIdList.toArray(),",");
            request.setVideoIds(join);
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuiLiException(20001,"删除视频失败");
        }
    }

}
