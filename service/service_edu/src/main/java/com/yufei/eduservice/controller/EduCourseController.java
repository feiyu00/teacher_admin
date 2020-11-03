package com.yufei.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yufei.commonutils.R;
import com.yufei.eduservice.entity.EduCourse;
import com.yufei.eduservice.entity.vo.CourseInfoVo;
import com.yufei.eduservice.entity.vo.CoursePublishVo;
import com.yufei.eduservice.entity.vo.CourseQuery;
import com.yufei.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-16
 */
@Api(tags = "课程")
@RestController
@RequestMapping("/eduservice/edu-course")
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    @ApiOperation("课程列表信息")
    @PostMapping("getCourseList/{current}/{limit}")
    public R getCourseList(@ApiParam(name = "current", value = "当前页码", required = true)
                           @PathVariable Long current,
                           @ApiParam(name = "limit", value = "每页记录数", required = true)
                           @PathVariable Long limit,
                           @RequestBody(required = false) CourseQuery queryCourse) {
        IPage<EduCourse> courseList = courseService.getCourseList(current, limit, queryCourse);
        List<EduCourse> records = courseList.getRecords();
        long total = courseList.getTotal();

        Map map = new HashMap();
        map.put("records", records);
        map.put("total", total);
        return R.ok().data(map);
    }

    @ApiOperation("添加课程信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    @ApiOperation("获取课程信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    @ApiOperation("修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @ApiOperation("修改课程信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo vo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", vo);
    }

    @ApiOperation("最终发布课程信息")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        boolean result = courseService.publishCourse(id);
        if (result) {
            return R.ok();
        } else {
            return R.error();
        }

    }
    @ApiOperation("删除课程信息")
    @DeleteMapping("removeCourse/{id}")
    public R removeCourse(@PathVariable String id) {
        boolean result = courseService.removeCourse(id);
        if (result) {
            return R.ok();
        } else {
            return R.error();
        }

    }


}

