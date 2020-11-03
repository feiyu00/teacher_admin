package com.yufei.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yufei.commonutils.R;
import com.yufei.eduservice.client.VodClient;
import com.yufei.eduservice.entity.EduVideo;
import com.yufei.eduservice.service.EduVideoService;
import com.yufei.servicebase.exceptionhandler.GuiLiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-16
 */
@Api(tags = "课程视频")
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private VodClient vodClient;
    @ApiOperation(value = "添加课程小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo video){
        eduVideoService.addVideo(video);
        return R.ok();
    }
    @ApiOperation(value = "获取课程小节")
    @GetMapping("getVideo/{id}")
    public R getVideo(@PathVariable String id){
        EduVideo eduVideo = eduVideoService.getVideo(id);
        return R.ok().data("video",eduVideo);
    }
    @ApiOperation(value = "修改课程小节")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo video){
        boolean b = eduVideoService.updateVideo(video);
        if (b){
            return R.ok();
        }else {
            return R.error();
        }

    }
    @ApiOperation(value = "删除课程小节")
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        EduVideo byId = eduVideoService.getById(id);
        //调用service-vod模块的serviceImp
        if (StringUtils.isEmpty(byId.getVideoSourceId())){
            R result = vodClient.removeAlyVideo(byId.getVideoSourceId());
            if (result.getCode() == 20001){
                throw new GuiLiException(20001,"删除视频失败，熔断器.............");
            }
        }
        eduVideoService.deleteVideo(id);
        return R.ok();
    }

}

