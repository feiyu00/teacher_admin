package com.yufei.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yufei.eduservice.entity.EduChapter;
import com.yufei.eduservice.entity.EduVideo;
import com.yufei.eduservice.entity.chapter.ChapterVo;
import com.yufei.eduservice.entity.chapter.VideoVo;
import com.yufei.eduservice.mapper.EduChapterMapper;
import com.yufei.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yufei.eduservice.service.EduVideoService;
import com.yufei.servicebase.exceptionhandler.GuiLiException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-16
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        wrapperChapter.orderByDesc("sort");
        List<EduChapter> eduChapters = baseMapper.selectList(wrapperChapter);
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        wrapperVideo.orderByDesc("sort");
        List<EduVideo> eduVideos = videoService.list(wrapperVideo);
        List<ChapterVo> finalList = new ArrayList<>();
        for (int i = 0; i < eduChapters.size(); i++) {
            EduChapter eduChapter = eduChapters.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            finalList.add(chapterVo);
            List<VideoVo> videoList = new ArrayList<>();
            for (int m = 0; m < eduVideos.size(); m++) {
                EduVideo eduVideo = eduVideos.get(m);
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoList);
        }

        return finalList;
    }

    @Override
    public void addChapter(EduChapter eduChapter) {
        int insert = baseMapper.insert(eduChapter);
        if (insert < 1) {
            throw new GuiLiException(20001, "添加章节失败");
        }
    }

    @Override
    public EduChapter getChapterInfo(String chapterId) {
        EduChapter eduChapter = baseMapper.selectById(chapterId);
        return eduChapter;
    }

    @Override
    public void updateChapter(EduChapter eduChapter) {
        int update = baseMapper.updateById(eduChapter);
        if (update < 1) {
            throw new GuiLiException(20001, "添加章节失败");
        }
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = videoService.count(wrapper);
        if (count > 0) {
            throw new GuiLiException(20001, "不能删除");
        } else {
            int delete = baseMapper.deleteById(chapterId);
            return delete > 0;
        }

    }

    @Override
    public void removeChapterByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
