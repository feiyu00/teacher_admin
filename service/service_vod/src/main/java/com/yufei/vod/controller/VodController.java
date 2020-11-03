package com.yufei.vod.controller;

import com.yufei.commonutils.R;
import com.yufei.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;
    //上传视频到阿里云
    @PostMapping("uploadAliVideo")
    public R uploadAliVideo(MultipartFile file) throws IOException {
        String videoId = vodService.uploadAliVideo(file);
        return R.ok().data("videoId",videoId);
    }
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id){
        return vodService.removeAlyVideo(id);
    }
    //删除多个视频
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList")List<String> videoIdList){
        vodService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }
}
