package com.yufei.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yufei.eduservice.client.VodClient;
import com.yufei.eduservice.entity.EduChapter;
import com.yufei.eduservice.entity.EduVideo;
import com.yufei.eduservice.mapper.EduVideoMapper;
import com.yufei.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yufei.servicebase.exceptionhandler.GuiLiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-16
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;

    @Override
    public void addVideo(EduVideo video) {
        baseMapper.insert(video);
    }

    @Override
    public EduVideo getVideo(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean updateVideo(EduVideo video) {
        int i = baseMapper.updateById(video);
        if (i == 0) {
            throw new GuiLiException(20001, "更新失败");
        } else {
            return i > 0;
        }
    }

    @Override
    public void deleteVideo(String id) {
        baseMapper.deleteById(id);
    }

    @Override
    public boolean removeVideoByCourseId(String id) {
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", id);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapperVideo);
        List<String> videoIds = new ArrayList<>();

        for (int i = 0; i < eduVideos.size(); i++) {
            try {
                String str = null;
                str = eduVideos.get(i).getVideoSourceId();
                if (!StringUtils.isEmpty(str)) {
                    videoIds.add(str);
                }
            } catch (Exception e) {

            }
        }

        vodClient.deleteBatch(videoIds);
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        int delete = baseMapper.delete(wrapper);
        return delete > 0;
    }
}
