package com.yufei.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yufei.eduservice.entity.EduChapter;
import com.yufei.eduservice.entity.EduCourse;
import com.yufei.eduservice.entity.EduCourseDescription;
import com.yufei.eduservice.entity.EduVideo;
import com.yufei.eduservice.entity.vo.CourseInfoVo;
import com.yufei.eduservice.entity.vo.CoursePublishVo;
import com.yufei.eduservice.entity.vo.CourseQuery;
import com.yufei.eduservice.mapper.EduCourseMapper;
import com.yufei.eduservice.service.EduChapterService;
import com.yufei.eduservice.service.EduCourseDescriptionService;
import com.yufei.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yufei.eduservice.service.EduVideoService;
import com.yufei.servicebase.exceptionhandler.GuiLiException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
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
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduVideoService videoService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.向课程表加数据
        //CourseInfoVo转换为EduCourse
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <=0 ){
            //添加失败
            throw new GuiLiException(20001,"添加课程信息失败");
        }
        //课程id
        String cid = eduCourse.getId();
        //2.向课程简介表添加课程简介
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(cid);
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.save(courseDescription);
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        BeanUtils.copyProperties(courseDescription,courseInfoVo);
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if (i <= 0){
            throw new GuiLiException(20001,"修改课程信息失败");
        }
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        boolean b = courseDescriptionService.updateById(description);
        if (!b){
            throw new GuiLiException(20001,"修改课程简介失败");
        }
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        return baseMapper.getPublishCourseInfo(id);
    }

    @Override
    public boolean publishCourse(String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        int i = baseMapper.updateById(eduCourse);
        if (i == 0) {
            throw  new GuiLiException(20001,"课程发布失败");
        }
        return i>0;
    }

    @Override
    public IPage<EduCourse> getCourseList(Long current, Long limit, CourseQuery queryCourse) {
        Page<EduCourse> page = new Page<>(current,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String status = queryCourse.getStatus();
        String title = queryCourse.getTitle();
        String begin = queryCourse.getBegin();
        String end = queryCourse.getEnd();
        if (!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        if (!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }
        IPage<EduCourse> eduCourseIPage = baseMapper.selectPage(page, wrapper);
        return eduCourseIPage ;
    }

    @Override
    public boolean removeCourse(String id) {
        boolean b = videoService.removeVideoByCourseId(id);
        if (b){
            chapterService.removeChapterByCourseId(id);
            courseDescriptionService.removeById(id);
        }
        int i = baseMapper.deleteById(id);
        if (i == 0 ){
            throw new  GuiLiException(20001,"删除失败");
        }
        return i > 0;
    }


}
