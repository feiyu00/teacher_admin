package com.yufei.eduservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yufei.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yufei.eduservice.entity.vo.CourseInfoVo;
import com.yufei.eduservice.entity.vo.CoursePublishVo;
import com.yufei.eduservice.entity.vo.CourseQuery;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-16
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);


    boolean publishCourse(String id);

    IPage<EduCourse> getCourseList(Long current, Long limit, CourseQuery queryCourse);

    boolean removeCourse(String id);
}
